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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.camphub.common.utils.DateUtils.parseStringToLocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OpenApiService {

    private final CampRepository campRepository;

    private final CampCodeService campCodeService;
    private final AreaCodeService areaCodeService;

    private final List<CampAssociationHelper> campAssociationHelpers;

    private final PropertiesValue propertiesValue;

    private final int numOfRows = 100;

    /**
     * 데이터 초기화
     */
    public void initializeCampList() {
        log.info("initializeCampList 시작");

        int maxPageCount = getMaxPageCount(null);

        List<OpenApiResponse.Item> itemList = getOpenApiResponseItemList(maxPageCount, null);
        log.info("initializeCampList 실행, itemList.size() = {}", itemList.size());

        if (itemList.isEmpty()) {
            log.info("initializeCampList 실행, 초기화 데이터 없음");
            return;
        }

        insertCampList(itemList);

        log.info("initializeCampList 완료");
    }

    /**
     * 데이터 동기화 및 수정
     */
    public void refreshCampListFromAPI(String modDate) {
        log.info("refreshCampListFromAPI 시작");

        int maxPageCount = getMaxPageCount(modDate);

        List<OpenApiResponse.Item> itemList = getOpenApiResponseItemList(maxPageCount, modDate);
        log.info("refreshCampListFromAPI 실행, itemList.size() = {}", itemList.size());

        if (itemList.isEmpty()) {
            log.info("refreshCampListFromAPI 실행, 리프레시 데이터 없음. 해당날짜 = {}", modDate);
            return;
        }

        //기존에 적재된 데이터인지 조회에 필요한 idList
        List<String> itemContentIds = itemList.stream()
                .map(OpenApiResponse.Item::getContentId)
                .collect(Collectors.toList());

        //Camp 데이터 조회 (영속성 컨텍스트)
        List<Camp> findCampList = campRepository.findCampsByCpIdIn(itemContentIds);
        Map<String, Camp> findCampMap = findCampList.stream()
                .collect(Collectors.toMap(Camp::getCpId, camp -> camp));

        /**
         * 변경사항 ID를 반복문으로 돌려 동기화 및 수정 반영
         * 1. DB를 통해 조회한 CampMaps에서 itemContentId를 이용하여 인스턴스를 가져온다. 인스턴스가 없을 경우 새로 추가되는 데이터로 간주
         *    (item.contentId == camp.cpId), 데이터 저장 List에 담은 후 continue를 통해 다음 반복으로 이동
         *
         * 2. 현재 DB의 수정날짜보다 Item 데이터로 넘어온 수정날짜가 이전일 경우 반복문을 건너 뛴다.
         *    현재 DB의 수정날짜와 Item 데이터로 넘어온 수정 날짜가 같을경우 동일한 수정사항이므로 반복문을 건너 뛴다.
         *
         * 3. 위의 두 조건이 일치하지 않을 경우 수정 대상 데이터로 간주, 데이터 업데이트 List에 담는다.
         */
        List<OpenApiResponse.Item> newCampItemList = new ArrayList<>();
        List<OpenApiResponse.Item> updateCampItemList = new ArrayList<>();

        for (OpenApiResponse.Item item : itemList) {
            //1
            Optional<Camp> optionalCamp = Optional.ofNullable(findCampMap.get(item.getContentId()));
            if (optionalCamp.isEmpty()) {
                newCampItemList.add(item);
                continue;
            }

            //2
            Camp camp = optionalCamp.get();
            LocalDateTime dbCpModDt = camp.getCpModDt();
            LocalDateTime itemModDt = parseStringToLocalDateTime(item.getModifiedtime());

            boolean beforeResult = itemModDt.isBefore(dbCpModDt);
            boolean equalResult = itemModDt.isEqual(dbCpModDt);

            if (beforeResult || equalResult) {
                continue;
            }

            //3
            updateCampItemList.add(item);
        }

        //저장 및 수정 진행
        insertCampList(newCampItemList);
        updateCampList(updateCampItemList, findCampMap);

        log.info("refreshCampListFromAPI 완료");
    }

    /**
     * 공공 데이터 포털 측에 API를 요청하여 응답 값을 받는 메서드
     */
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

    /**
     * 데이터 초기화 및 데이터 리프레쉬 시에 요청할 페이지의 수를 구하는 메서드
     * OpenAPI 요청은 1번에 최대 100개 가능
     */
    private int getMaxPageCount(String modDate) {
        Mono<OpenApiResponse> findTotalCountResponse = fetchCampList(1, 1, modDate);
        int totalCount = findTotalCountResponse.map(openApiResponse -> openApiResponse.getResponse().getBody().getTotalCount()).block().intValue();

        log.info("getMaxPageCount 실행, totalCount = {}",  totalCount);

        return calculatePagesRequired(totalCount);
    }

    /**
     * 요청 페이지 수를 구하는 메서드
     */
    private int calculatePagesRequired(int rowCount) {
        return rowCount%numOfRows==0 ? rowCount/numOfRows : (rowCount/numOfRows)+1;
    }

    /**
     * 요청 페이지 수 만큼의 응답값을 모아 하나의 Item List로 변환하는 메서드
     * 하나의 페이지당 한 개의 OpenApiResponse 응답을 반환, 응답안의 Item 인스턴스들을 flatMap을 통해 하나의 리스트로 반환
     */
    private List<OpenApiResponse.Item> getOpenApiResponseItemList(int maxPageCount, String modDate) {
        log.info("maxPageCount = {}", maxPageCount);
        log.info("modDate = {}", modDate == null ? "날짜 없음" : modDate);

        List<OpenApiResponse> openApiResponseList = Flux.range(1, maxPageCount)
                .flatMap(page -> fetchCampList(numOfRows, page, modDate) // 각 페이지에 대한 요청
                                .subscribeOn(Schedulers.parallel()), // 병렬 처리
                        5)//동시 실행할 작업의 최대 수
                .collectList()
                .block();

        return openApiResponseList.stream()
                .flatMap(openApiResponse -> openApiResponse.getResponse().getBody().getItems().getItem().stream())
                .collect(Collectors.toList());
    }

    /**
     * DB에 캠핑 정보를 Insert 하는 메서드
     */
    private void insertCampList(List<OpenApiResponse.Item> itemList) {
        log.info("insertCampList 시작");

        //캠프코드, 시도코드, 시군구 코드
        Map<String, Map<String, CampCode>> nameToCodeMaps = campCodeService.getNameToCodeMaps();
        Map<String, ProvinceCode> nameToProvinceCodeMap = areaCodeService.getNameToProvinceCodeMap();
        Map<String, DistrictCode> nameToDistrictCodeMap = areaCodeService.getNameToDistrictCodeMap();

        for (OpenApiResponse.Item item : itemList) {
            Camp camp = Camp.apiToEntity(item, nameToProvinceCodeMap, nameToDistrictCodeMap);
            CampDetail.apiToEntity(item).linkToCamp(camp);
            CampFacility.apiToEntity(item).linkToCamp(camp);
            CampSite.apiToEntity(item).linkToCamp(camp);

            campRepository.save(camp);

            campAssociationHelpers.forEach(campAssociationHelper -> {
                campAssociationHelper.insertCampAssociations(item, camp, nameToCodeMaps, CampAssociationHelper.INSERT);
            });
        }

        log.info("insertCampList 종료, saveCampList.size() = {}", itemList.size());
    }

    private void updateCampList(List<OpenApiResponse.Item> itemList, Map<String, Camp> campMap) {
        log.info("updateCampList 시작");

        //캠프코드, 시도코드, 시군구 코드
        Map<String, Map<String, CampCode>> nameToCodeMaps = campCodeService.getNameToCodeMaps();
        Map<String, ProvinceCode> nameToProvinceCodeMap = areaCodeService.getNameToProvinceCodeMap();
        Map<String, DistrictCode> nameToDistrictCodeMap = areaCodeService.getNameToDistrictCodeMap();

        for (OpenApiResponse.Item item : itemList) {
            Camp camp = campMap.get(item.getContentId());
            CampDetail campDetail = camp.getCampDetail();
            CampFacility campFacility = camp.getCampFacility();
            CampSite campSite = camp.getCampSite();

            camp.updateCamp(item, nameToProvinceCodeMap, nameToDistrictCodeMap);
            campDetail.updateCampDetail(item);
            campFacility.updateCampFacility(item);
            campSite.updateCampSite(item);

            campAssociationHelpers.forEach(campAssociationHelper -> {
                campAssociationHelper.updateCampAssociations(item, camp, nameToCodeMaps);
            });
        }

        log.info("updateCampList 종료, updateCampList.size() = {}", itemList.size());
    }
}
