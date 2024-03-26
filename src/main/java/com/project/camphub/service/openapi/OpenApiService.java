package com.project.camphub.service.openapi;

import com.project.camphub.config.webclient.PropertiesValue;
import com.project.camphub.config.webclient.WebClientFactory;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.domain.openapi.entity.TempCamp;
import com.project.camphub.domain.openapi.entity.TempCampDetail;
import com.project.camphub.domain.openapi.entity.TempCampFacility;
import com.project.camphub.domain.openapi.entity.TempCampSite;
import com.project.camphub.repository.openapi.TempCampDetailRepository;
import com.project.camphub.repository.openapi.TempCampFacilityRepository;
import com.project.camphub.repository.openapi.TempCampRepository;
import com.project.camphub.repository.openapi.TempCampSiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final TempCampRepository tempCampRepository;
    private final TempCampDetailRepository tempCampDetailRepository;
    private final TempCampFacilityRepository tempCampFacilityRepository;
    private final TempCampSiteRepository tempCampSiteRepository;

    private final PropertiesValue propertiesValue;

    private final int numOfRows = 100;

    public void fetchAndInsertCampList() {
        //데이터 total 개수 확인
        Mono<OpenApiResponse> findTotalCountResponse = fetchCampList(1, 1);
        int totalCount = findTotalCountResponse.map(openApiResponse -> openApiResponse.getResponse().getBody().getTotalCount()).block().intValue();

        //조회할 페이지 수 저장
        int maxPageCount = calculatePagesRequired(totalCount);
        log.info("maxPageCount = {}", maxPageCount);

        Flux.range(1, maxPageCount)
                .flatMap(page -> fetchCampList(numOfRows, page) // 각 페이지에 대한 요청
                                .subscribeOn(Schedulers.parallel()), // 병렬 처리
                        5)//동시 실행할 작업의 최대 수
                .subscribe(this::insertCampList);
    }

    private void insertCampList(OpenApiResponse openApiResponse) {
        OpenApiResponse.Body body = openApiResponse.getResponse().getBody();
        int pageNo = body.getPageNo();

        log.info("page={}, insertCampList 진입", pageNo);

        List<OpenApiResponse.Item> itemList = body.getItems().getItem();

        for (OpenApiResponse.Item item : itemList) {
            String contentId = item.getContentId();

            TempCamp tempCamp = new TempCamp();
            tempCamp.setContentId(item.getContentId());
            tempCamp.setFacltNm(item.getFacltNm());
            tempCamp.setTel(item.getTel());
            tempCamp.setHomepage(item.getHomepage());
            tempCamp.setResveCl(item.getResveCl());
            tempCamp.setResveUrl(item.getResveUrl());
            tempCamp.setOperPdCl(item.getOperPdCl());
            tempCamp.setOperDeCl(item.getOperDeCl());
            tempCamp.setHvofBgnde(item.getHvofBgnde());
            tempCamp.setHvofEnddle(item.getHvofEnddle());
            tempCamp.setInduty(item.getInduty());
            tempCamp.setLctCl(item.getLctCl());
            tempCamp.setThemaEnvrnCl(item.getThemaEnvrnCl());
            tempCamp.setTourEraCl(item.getTourEraCl());
            tempCamp.setFirstImageUrl(item.getFirstImageUrl());
            tempCamp.setManageSttus(item.getManageSttus());
            tempCamp.setMangeDivNm(item.getMangeDivNm());
            tempCamp.setMgcDiv(item.getMgcDiv());
            tempCamp.setFacltDivNm(item.getFacltDivNm());
            tempCamp.setInsrncAt(item.getInsrncAt());
            tempCamp.setTrsagntNo(item.getTrsagntNo());
            tempCamp.setBizrno(item.getBizrno());
            tempCamp.setPrmisnDe(item.getPrmisnDe());
            tempCamp.setCreatedtime(item.getCreatedtime());
            tempCamp.setModifiedtime(item.getModifiedtime());
            tempCamp.setSyncStatus(item.getSyncStatus());

            TempCampDetail tempCampDetail = new TempCampDetail();
            tempCampDetail.setContentId(item.getContentId());
            tempCampDetail.setIntro(item.getIntro());
            tempCampDetail.setLineIntro(item.getLineIntro());
            tempCampDetail.setFeatureNm(item.getFeatureNm());
            tempCampDetail.setTooltip(item.getTooltip());
            tempCampDetail.setDirection(item.getDirection());
            tempCampDetail.setDoNm(item.getDoNm());
            tempCampDetail.setSigunguNm(item.getSigunguNm());
            tempCampDetail.setZipcode(item.getZipcode());
            tempCampDetail.setAddr1(item.getAddr1());
            tempCampDetail.setAddr2(item.getAddr2());
            tempCampDetail.setMapX(item.getMapX());
            tempCampDetail.setMapY(item.getMapY());
            tempCampDetail.setClturEventAt(item.getClturEventAt());
            tempCampDetail.setClturEvent(item.getClturEvent());
            tempCampDetail.setExprnProgrmAt(item.getExprnProgrmAt());
            tempCampDetail.setExprnProgrm(item.getExprnProgrm());
            tempCampDetail.setEqpmnLendCl(item.getEqpmnLendCl());
            tempCampDetail.setAnimalCmgCl(item.getAnimalCmgCl());
            tempCampDetail.setCaravAcmpnyAt(item.getCaravAcmpnyAt());
            tempCampDetail.setTrlerAcmpnyAt(item.getTrlerAcmpnyAt());

            TempCampFacility tempCampFacility = new TempCampFacility();
            tempCampFacility.setContentId(item.getContentId());
            tempCampFacility.setManageNmpr(item.getManageNmpr());
            tempCampFacility.setAllar(item.getAllar());
            tempCampFacility.setSbrsCl(item.getSbrsCl());
            tempCampFacility.setSbrsEtc(item.getSbrsEtc());
            tempCampFacility.setPosblFcltyCl(item.getPosblFcltyCl());
            tempCampFacility.setPosblFcltyEtc(item.getPosblFcltyEtc());
            tempCampFacility.setGnrlSiteCo(item.getGnrlSiteCo());
            tempCampFacility.setAutoSiteCo(item.getAutoSiteCo());
            tempCampFacility.setGlampSiteCo(item.getGlampSiteCo());
            tempCampFacility.setCaravSiteCo(item.getCaravSiteCo());
            tempCampFacility.setIndvdlCaravSiteCo(item.getIndvdlCaravSiteCo());
            tempCampFacility.setGlampInnerFclty(item.getGlampInnerFclty());
            tempCampFacility.setCaravInnerFclty(item.getCaravInnerFclty());
            tempCampFacility.setBrazierCl(item.getBrazierCl());
            tempCampFacility.setWtrplCo(item.getWtrplCo());
            tempCampFacility.setToiletCo(item.getToiletCo());
            tempCampFacility.setSwrmCo(item.getSwrmCo());
            tempCampFacility.setExtshrCo(item.getExtshrCo());
            tempCampFacility.setFrprvtWrppCo(item.getFrprvtWrppCo());
            tempCampFacility.setFrprvtSandCo(item.getFrprvtSandCo());
            tempCampFacility.setFireSensorCo(item.getFireSensorCo());

            TempCampSite tempCampSite = new TempCampSite();
            tempCampSite.setContentId(item.getContentId());
            tempCampSite.setSitedStnc(item.getSitedStnc());
            tempCampSite.setSiteMg1Co(item.getSiteMg1Co());
            tempCampSite.setSiteMg2Co(item.getSiteMg2Co());
            tempCampSite.setSiteMg3Co(item.getSiteMg3Co());
            tempCampSite.setSiteMg1Width(item.getSiteMg1Width());
            tempCampSite.setSiteMg1Vrticl(item.getSiteMg1Vrticl());
            tempCampSite.setSiteMg2Width(item.getSiteMg2Width());
            tempCampSite.setSiteMg2Vrticl(item.getSiteMg2Vrticl());
            tempCampSite.setSiteMg3Width(item.getSiteMg3Width());
            tempCampSite.setSiteMg3Vrticl(item.getSiteMg3Vrticl());
            tempCampSite.setSiteBottomCl1(item.getSiteBottomCl1());
            tempCampSite.setSiteBottomCl2(item.getSiteBottomCl2());
            tempCampSite.setSiteBottomCl3(item.getSiteBottomCl3());
            tempCampSite.setSiteBottomCl4(item.getSiteBottomCl4());
            tempCampSite.setSiteBottomCl5(item.getSiteBottomCl5());

            tempCampRepository.save(tempCamp);
            tempCampDetailRepository.save(tempCampDetail);
            tempCampFacilityRepository.save(tempCampFacility);
            tempCampSiteRepository.save(tempCampSite);
        }

        log.info("page={}, insertCampList 종료", pageNo);
    }

    private Mono<OpenApiResponse> fetchCampList(int numOfRows, int page) {
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
}
