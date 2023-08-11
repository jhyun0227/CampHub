package com.project.camphub.externalapi.service;

import com.project.camphub.aop.annotation.OpenApiTime;
import com.project.camphub.camp.entity.Camp;
import com.project.camphub.camp.entity.CampDetail;
import com.project.camphub.camp.entity.CampFacility;
import com.project.camphub.camp.entity.CampSite;
import com.project.camphub.camp.repository.CampDetailRepository;
import com.project.camphub.camp.repository.CampFacilityRepository;
import com.project.camphub.camp.repository.CampRepository;
import com.project.camphub.camp.repository.CampSiteRepository;
import com.project.camphub.externalapi.dto.PropertiesValue;
import com.project.camphub.externalapi.dto.openapi.Item;
import com.project.camphub.externalapi.dto.openapi.OpenApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OpenApiService {

    //API 객체
    private WebClient webClient;

    private final PropertiesValue propertiesValue; //프로퍼티 값이 담긴 객체

    //비즈니스 객체
    private final CampRepository campRepository;
    private final CampDetailRepository campDetailRepository;
    private final CampFacilityRepository campFacilityRepository;
    private final CampSiteRepository campSiteRepository;

    @PostConstruct
    private void setWebClient() {
        /**
         * 노션 정리 1
         * WebClient의 인코딩방식이 UriComponentsBuilder#encode() 라는 옵션을 사용한다.
         * 이 옵션은 url의 예약 문자들을 치환하기 때문에, WebClient의 기본 인코딩으로 인해 Key 값이 변경되는 문제가 발생
         * url 인코딩 모드를 없애기 위해 DefaultUriBuilderFactory 클래스를 사용했다.
         */
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(propertiesValue.getEndPoint());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        webClient = WebClient.builder().uriBuilderFactory(factory).build();
    }

    /**
     * OpenApi에서 가져온 데이터를 DB에 저장하는 메서드
     */
    @OpenApiTime
    public String campInfo() {

        int numOfRows = 1;
        int pageNo = 1;
        int totalCount;

        OpenApiResponse campInfo = this.getCampInfo(numOfRows, pageNo);
        log.info("getCampInfo 실행, campInfo = {}", campInfo);

        this.saveCampInfo(campInfo.getResponse().getBody().getItems().getItem());

        return "ok";
    }

    /**
     * OpenApi에서 데이터를 가져오는 메서드
     */
    private OpenApiResponse getCampInfo(int numOfRows, int pageNo) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(propertiesValue.getBasePath())
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("pageNo", pageNo)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "CampHub")
                        .queryParam("serviceKey", propertiesValue.getEncodingKey())
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(OpenApiResponse.class)
                .block();
    }

    /**
     * 가져온 데이터를 데이터베이스에 저장하는 메서드
     */
    private void saveCampInfo(List<Item> items) {
        for (Item item : items) {
            //캠프 생성
            Camp camp = Camp.fromOpenApiResponse(item);
            campRepository.save(camp);

            //캠프상세 생성
            CampDetail campDetail = CampDetail.fromOpenApiResponse(camp, item);
            campDetailRepository.save(campDetail);

            //캠프시설 생성
            CampFacility campFacility = CampFacility.fromOpenApiResponse(camp, item);
            campFacilityRepository.save(campFacility);

            //캠프사이트 생성
            CampSite campSite = CampSite.fromOpenApiResponse(camp, item);
            campSiteRepository.save(campSite);
        }
    }

    /**
     * openApiResponse
     */
}
