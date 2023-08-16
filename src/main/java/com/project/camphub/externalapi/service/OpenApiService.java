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
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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
         * 노션 정리1
         * WebClient의 인코딩방식이 UriComponentsBuilder#encode() 라는 옵션을 사용한다.
         * 이 옵션은 url의 예약 문자들을 치환하기 때문에, WebClient의 기본 인코딩으로 인해 Key 값이 변경되는 문제가 발생
         * url 인코딩 모드를 없애기 위해 DefaultUriBuilderFactory 클래스를 사용했다.
         *
         * 노션 정리4
         * org.springframework.core.io.buffer.DataBufferLimitException: Exceeded limit on max bytes to buffer : 262144
         * WebClient에 설정되는 Default codec buffer size가 256KB로 제한된다. 애플리케이션 메모리 문제 때문....
         * campInfo() 메서드로 인해 메모리를 많이 사용하게 되어서 발생하는 에러인데
         * codec의 buffer size를 10MB로 올려주었다.
         */
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(propertiesValue.getEndPoint());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)).build();

        webClient = WebClient.builder().uriBuilderFactory(factory).exchangeStrategies(strategies).build();
    }

    /**
     * OpenApi에서 가져온 데이터를 DB에 저장하는 메서드
     */
    @OpenApiTime
    public int campInfo() {

        int numOfRows = 100;
        int pageNo = 1;
        int totalCount = 0;
        int loopCount = 0;
        boolean isFirstIter = true;

        do {
            OpenApiResponse campInfo = this.getCampInfo(numOfRows, pageNo);

            this.saveCampInfo(campInfo.getResponse().getBody().getItems().getItem());

            if (isFirstIter) {
                totalCount = campInfo.getResponse().getBody().getTotalCount();
                log.info("totalCount = {}",  totalCount);
                loopCount = totalCount % numOfRows==0 ? totalCount / numOfRows : totalCount / numOfRows + 1;
                log.info("loopCount = {}", loopCount);

                isFirstIter = false;
            }

            log.info("pageNo = {}",  pageNo);

            pageNo += 1;

        } while (pageNo <= loopCount);

        return totalCount;
    }

    /**
     * OpenApi에서 데이터를 가져오는 메서드
     *
     * 2023.08.16 변경사항
     * 원래 OpenApi의 '/basedList(기본 정보 목록 조회)'를 사용하였지만, 해당 데이터는 삭제 되거나, 비공개 데이터는 가져오지 않음 (syncStatus = 'D')
     * DB 데이터 저장 방식이므로 '/basedSyncList(동기화 목록 조회)'를 이용하여 전체 데이터를 가져오는 것으로 변경
     */
    private OpenApiResponse getCampInfo(int numOfRows, int pageNo) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(propertiesValue.getSyncPath())
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

            //캠프 상세, 시설, 사이트 생성
            CampDetail campDetail = CampDetail.fromOpenApiResponse(camp, item);
            CampFacility campFacility = CampFacility.fromOpenApiResponse(camp, item);
            CampSite campSite = CampSite.fromOpenApiResponse(camp, item);

            camp.refCpdCpfCps(campDetail, campFacility, campSite);
            campRepository.save(camp);

            /*
            원래 처음 작성했던 코드
            아래에 따로 저장하는 형식으로 코드를 작성했었는데 에러가 계속 발생...
            이유를 잘모르겠음.. 일단 더 공부...

            [단방향 테스트]
            Camp의 다른 세개의 참조를 지운 후 단반향으로 테스트
            Camp를 먼저 save하면 에러가 발생,
            Camp 객체를 생성만하고 다른 세개의 객체를 참조 시킨다음에 마지막에 save하면 에러가 발생하지 않음...

            Camp를 먼저 save한 후 flush 후 clear를 하면 정상적으로 동작....
            ChatGpt의 설명으로는 Camp를 save 할 때 프록시가 생성이되고, CampDetail에서 Camp를 참조할때 또 프록시가 생성이 되어서
            두 개의 프록시가 생성이 되어 그렇다는데.. 잘 이해가 안된다.
            */

            /*
            Camp camp = Camp.fromOpenApiResponse(item);
            campRepository.save(camp);

            //entityManager.flush();
            //entityManager.clear();

            //캠프상세 생성
            CampDetail campDetail = CampDetail.fromOpenApiResponse(camp, item);
            campDetailRepository.save(campDetail);
            log.info("campDetail = {}", campDetail);

            //캠프시설 생성
            CampFacility campFacility = CampFacility.fromOpenApiResponse(camp, item);
            campFacilityRepository.save(campFacility);
            log.info("campFacility = {}", campFacility);


            //캠프사이트 생성
            CampSite campSite = CampSite.fromOpenApiResponse(camp, item);
            campSiteRepository.save(campSite);
            log.info("campsite = {}", campSite);

            //campRepository.save(camp);

            camps.add(camp);
            */
        }
    }

    /**
     * OpenApi에서 날짜를 기준으로 데이터를 동기화 하여 수정하는 메서드
     * OpenApi syncStatus : A(신규), U(수정), D(삭제)
     * A : 새로운 데이터이므로 DB에 저장
     * U : 데이터 수정이므로 DB에서 값을 가져와서 수정
     * D : TourApi DB상으로 삭제된 데이터이거나 비공개 데이터를 의미한다.
     */
    @OpenApiTime
    public String campSyncInfo(String searchDate) {

        OpenApiResponse syncCampInfo = this.getSyncCampInfo(100, 1, searchDate);
        log.info("syncCampInfo = {}", syncCampInfo);


        return "ok";
    }

    /**
     * OpenApi에서 날짜를 기준으로 변경사항이 있는 데이터를 가져오는 메서드
     */
    private OpenApiResponse getSyncCampInfo(int numOfRows, int pageNo, String searchDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(propertiesValue.getSyncPath())
                        .queryParam("numOfRows", numOfRows)
                        .queryParam("pageNo", pageNo)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "CampHub")
                        .queryParam("serviceKey", propertiesValue.getEncodingKey())
                        .queryParam("_type", "json")
                        .queryParam("syncModTime", searchDate)
                        .build())
                .retrieve()
                .bodyToMono(OpenApiResponse.class)
                .block();
    }
}
