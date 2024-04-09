package com.project.camphub.service.openapi;

import com.project.camphub.domain.common.registry.AreaMapRegistry;
import com.project.camphub.config.webclient.PropertiesValue;
import com.project.camphub.config.webclient.WebClientFactory;
import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.CampDetail;
import com.project.camphub.domain.camp.entity.CampFacility;
import com.project.camphub.domain.camp.entity.CampSite;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.CampDetailRepository;
import com.project.camphub.repository.camp.CampFacilityRepository;
import com.project.camphub.repository.camp.CampRepository;
import com.project.camphub.repository.camp.CampSiteRepository;
import com.project.camphub.service.camp.helper.CampCodeHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OpenApiService {

    private final CampRepository campRepository;
    private final CampDetailRepository campDetailRepository;
    private final CampFacilityRepository campFacilityRepository;
    private final CampSiteRepository campSiteRepository;

    private final List<CampCodeHelper> campCodeHelpers;
    private final AreaMapRegistry areaMapRegistry;
    private final PropertiesValue propertiesValue;

    private final int numOfRows = 100;

    public void fetchAndInsertCampList() {
        //데이터 total 개수 확인
        Mono<OpenApiResponse> findTotalCountResponse = fetchCampList(1, 1);
        int totalCount = findTotalCountResponse.map(openApiResponse -> openApiResponse.getResponse().getBody().getTotalCount()).block().intValue();

        //조회할 페이지 수 저장
        int maxPageCount = calculatePagesRequired(totalCount);
        log.info("maxPageCount = {}", maxPageCount);

        List<OpenApiResponse> openApiResponseList = Flux.range(1, 1)
                .flatMap(page -> fetchCampList(numOfRows, page) // 각 페이지에 대한 요청
                                .subscribeOn(Schedulers.parallel()), // 병렬 처리
                        10)//동시 실행할 작업의 최대 수
                .collectList()
                .block();

        if (!openApiResponseList.isEmpty()) {
            log.info("openApiResponse.size() = {}", openApiResponseList.size());
            openApiResponseList.forEach(this::insertCampList);
        }
    }

    private Mono<OpenApiResponse> fetchCampList(int numOfRows, int page) {
        log.info("fetchCampList 실행, page={}", page);

        WebClient webClient = WebClientFactory.createWebClient(propertiesValue.getOpenApiEndPoint());

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(propertiesValue.getOpenApiSyncPath())
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("pageNo", page)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "CampHub")
                        .queryParam("serviceKey", propertiesValue.getOpenApiEncodingKey())
                        .queryParam("_type", "json")
                        .build()
                )
                .retrieve()
                .bodyToMono(OpenApiResponse.class);
    }

    private int calculatePagesRequired(int rowCount) {
        return rowCount%numOfRows==0 ? rowCount/numOfRows : (rowCount/numOfRows)+1;
    }

    private void insertCampList(OpenApiResponse openApiResponse) {
        OpenApiResponse.Body body = openApiResponse.getResponse().getBody();
        int pageNo = body.getPageNo();

        log.info("insertCampList 실행, page={}", pageNo);

        List<OpenApiResponse.Item> itemList = body.getItems().getItem();

        for (OpenApiResponse.Item item : itemList) {
            Camp camp = campRepository.save(Camp.apiToEntity(item, areaMapRegistry));

            campDetailRepository.save(CampDetail.apiToEntity(item, camp));
            campFacilityRepository.save(CampFacility.apiToEntity(item, camp));
            campSiteRepository.save(CampSite.apiToEntity(item, camp));

            campCodeHelpers.forEach(campCodeHelper -> {
                campCodeHelper.saveCampCode(
                        campCodeHelper.getCampCodeEntity(item, camp));
            });
        }

        log.info("insertCampList 종료, page={}", pageNo);
    }
}
