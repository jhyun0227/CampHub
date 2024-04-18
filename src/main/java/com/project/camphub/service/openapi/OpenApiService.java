package com.project.camphub.service.openapi;

import com.project.camphub.config.webclient.PropertiesValue;
import com.project.camphub.config.webclient.WebClientFactory;
import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.CampDetail;
import com.project.camphub.domain.camp.entity.CampFacility;
import com.project.camphub.domain.camp.entity.CampSite;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.common.entity.area.DistrictCode;
import com.project.camphub.domain.common.entity.area.ProvinceCode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.CampDetailRepository;
import com.project.camphub.repository.camp.CampFacilityRepository;
import com.project.camphub.repository.camp.CampRepository;
import com.project.camphub.repository.camp.CampSiteRepository;
import com.project.camphub.service.camp.code.CampCodeService;
import com.project.camphub.service.camp.helper.CampAssociationHelper;
import com.project.camphub.service.common.area.AreaCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OpenApiService {

    private final CampRepository campRepository;
    private final CampDetailRepository campDetailRepository;
    private final CampFacilityRepository campFacilityRepository;
    private final CampSiteRepository campSiteRepository;

    private final CampCodeService campCodeService;
    private final AreaCodeService areaCodeService;

    private final List<CampAssociationHelper> campAssociationHelpers;

    private final PropertiesValue propertiesValue;

    private final int numOfRows = 100;

    public void initializeCampList() {
        int maxPageCount = getMaxPageCount(null);

        List<OpenApiResponse> openApiResponseList = getOpenApiResponseList(maxPageCount, null);

        if (!openApiResponseList.isEmpty()) {
            insertCampList(openApiResponseList);
        }
    }

    public void refreshCampListFromAPI(String modDate) {
        int maxPageCount = getMaxPageCount(modDate);

        List<OpenApiResponse> openApiResponseList = getOpenApiResponseList(maxPageCount, modDate);

        if (!openApiResponseList.isEmpty()) {
            updateCampList(openApiResponseList);
        }
    }

    private Mono<OpenApiResponse> fetchCampList(int numOfRows, int page, String modDate) {
        log.info("fetchCampList 실행, page={}", page);

        WebClient webClient = WebClientFactory.createWebClient(propertiesValue.getOpenApiEndPoint());

        return webClient.get()
                .uri(uriBuilder -> {
                            UriBuilder builder = uriBuilder
                                    .path(propertiesValue.getOpenApiSyncPath())
                                    .queryParam("numOfRows", numOfRows)
                                    .queryParam("pageNo", page)
                                    .queryParam("MobileOS", "ETC")
                                    .queryParam("MobileApp", "CampHub")
                                    .queryParam("serviceKey", propertiesValue.getOpenApiEncodingKey())
                                    .queryParam("_type", "json");

                            if (StringUtils.hasText(modDate)) {
                                builder.queryParam("syncModTime", modDate);
                            }

                            return builder.build();
                        }
                )
                .retrieve()
                .bodyToMono(OpenApiResponse.class);
    }

    private int getMaxPageCount(String modDate) {
        Mono<OpenApiResponse> findTotalCountResponse = fetchCampList(1, 1, modDate);
        int totalCount = findTotalCountResponse.map(openApiResponse -> openApiResponse.getResponse().getBody().getTotalCount()).block().intValue();

        return calculatePagesRequired(totalCount);
    }

    private int calculatePagesRequired(int rowCount) {
        return rowCount%numOfRows==0 ? rowCount/numOfRows : (rowCount/numOfRows)+1;
    }

    private List<OpenApiResponse> getOpenApiResponseList(int maxPageCount, String modDate) {
        log.info("maxPageCount = {}", maxPageCount);
        log.info("modDate = {}", modDate == null ? "날짜 없음" : modDate);

        return Flux.range(1, maxPageCount)
                .flatMap(page -> fetchCampList(numOfRows, page, modDate) // 각 페이지에 대한 요청
                                .subscribeOn(Schedulers.parallel()), // 병렬 처리
                        5)//동시 실행할 작업의 최대 수
                .collectList()
                .block();
    }

    private void insertCampList(List<OpenApiResponse> openApiResponseList) {
        //캠프코드, 시도코드, 시군구 코드
        Map<String, Map<String, CampCode>> nameToCodeMaps = campCodeService.getNameToCodeMaps();
        Map<String, ProvinceCode> nameToProvinceCodeMap = areaCodeService.getNameToProvinceCodeMap();
        Map<String, DistrictCode> nameToDistrictCodeMap = areaCodeService.getNameToDistrictCodeMap();

        for (OpenApiResponse openApiResponse : openApiResponseList) {

            OpenApiResponse.Body body = openApiResponse.getResponse().getBody();
            int pageNo = body.getPageNo();

            List<OpenApiResponse.Item> itemList = body.getItems().getItem();

            for (OpenApiResponse.Item item : itemList) {
                Camp camp = Camp.apiToEntity(item, nameToProvinceCodeMap, nameToDistrictCodeMap);

                campRepository.save(camp);
                campDetailRepository.save(CampDetail.apiToEntity(item, camp));
                campFacilityRepository.save(CampFacility.apiToEntity(item, camp));
                campSiteRepository.save(CampSite.apiToEntity(item, camp));

                campAssociationHelpers.forEach(campAssociationHelper -> {
                    List<?> campAssociationEntity = campAssociationHelper.getCampAssociationEntity(item, camp, nameToCodeMaps);
                    if (campAssociationEntity != null) {
                        campAssociationHelper.saveCampAssociation(campAssociationEntity);
                    }
                });
            }

            log.info("insertCampList 종료, page={}", pageNo);
        }
    }

    private void updateCampList(List<OpenApiResponse> openApiResponseList) {
        //캠프코드, 시도코드, 시군구 코드
        Map<String, Map<String, CampCode>> nameToCodeMaps = campCodeService.getNameToCodeMaps();
        Map<String, ProvinceCode> nameToProvinceCodeMap = areaCodeService.getNameToProvinceCodeMap();
        Map<String, DistrictCode> nameToDistrictCodeMap = areaCodeService.getNameToDistrictCodeMap();

        //ContentId로 Item 찾는 맵
        Map<String, OpenApiResponse.Item> itemMaps = new HashMap<>();
        //엔티티 조회를 위한 Id 리스트
        List<String> updateContentIds = new ArrayList<>();

        openApiResponseList.stream()
                .flatMap(openApiResponse -> openApiResponse.getResponse().getBody().getItems().getItem().stream())
                .forEach(item -> {
                    itemMaps.put(item.getContentId(), item);
                    updateContentIds.add(item.getContentId());
                });

        

    }
}
