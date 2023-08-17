package com.project.camphub.externalapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.camphub.aop.annotation.OpenApiTime;
import com.project.camphub.camp.entity.Camp;
import com.project.camphub.camp.entity.CampDetail;
import com.project.camphub.camp.entity.CampFacility;
import com.project.camphub.camp.entity.CampSite;
import com.project.camphub.camp.repository.CampDetailRepository;
import com.project.camphub.camp.repository.CampFacilityRepository;
import com.project.camphub.camp.repository.CampRepository;
import com.project.camphub.camp.repository.CampSiteRepository;
import com.project.camphub.exception.externalapi.ExternalApiError;
import com.project.camphub.exception.externalapi.ExternalApiException;
import com.project.camphub.externalapi.dto.PropertiesValue;
import com.project.camphub.externalapi.dto.openapi.Item;
import com.project.camphub.externalapi.dto.openapi.ItemMapDto;
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
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OpenApiService {

    private WebClient webClient;
    private final ObjectMapper objectMapper;
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
        boolean boolLoop = true;

        do {
            OpenApiResponse campInfo = this.getCampInfo(numOfRows, pageNo);

            this.saveCampInfo(campInfo.getResponse().getBody().getItems().getItem());

            if (isFirstIter) {
                totalCount = campInfo.getResponse().getBody().getTotalCount();
                log.info("totalCount = {}",  totalCount);
                loopCount = totalCount % numOfRows == 0 ? totalCount / numOfRows : totalCount / numOfRows + 1;
                log.info("loopCount = {}", loopCount);

                isFirstIter = false;
            }

            log.info("pageNo = {}",  pageNo);

            pageNo += 1;

            //페이지번호가 반복횟수보다 많으면 중지한다.
            if (pageNo > loopCount) {
                boolLoop = false;
            }

        } while (boolLoop);

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
     * syncStatus가 A인 경우 데이터를 저장하고, U와 D인 경우에는 수정을 한다.
     */
    @OpenApiTime
    public String campSyncInfo(String searchDate) {

        ItemMapDto itemMapDto = this.iterSyncCampInfo(searchDate);
        List<Item> newCamps = itemMapDto.getNewCamps();
        Map<String, Item> updatedCamps = itemMapDto.getUpdatedCamps();
        log.info("newCamps.size() = {}", newCamps.size());
        log.info("updatedCamps.size() = {}", updatedCamps.size());

        if (newCamps.size() == 0 && updatedCamps.size() == 0) {
            return "신규 캠프, 변경된 캠프가 존재하지 않습니다.";
        }

        if (newCamps.size() != 0) {
            this.saveCampInfo(newCamps);
        }

        if (updatedCamps.size() != 0) {
            this.updateCampInfo(updatedCamps);
        }

        return "정상적으로 데이터 동기화 하였습니다.";
    }

    /**
     * 날짜 기반의 동기화 데이터를 반복문을 실행하여 가져오는 메서드
     */
    private ItemMapDto iterSyncCampInfo(String searchDate) {

        int numOfRows = 100;
        int pageNo = 1;
        int totalCount = 0;
        int loopCount = 0;

        boolean isFirstIter = true;
        boolean boolLoop = true;

        /**
         * 동기화 사항이 있는 정보들을 저장하는 Collection
         * syncStatus가 A일 경우 newCamp Map에 저장
         * syncStatus가 U, D일 경우 updatedCamp Map에 저장
         */
        ItemMapDto itemMapDto = new ItemMapDto();

        do {
            /**
             * Sync의 경우 해당 날짜의 변경사항이 없다면 JSON에서 items가 배열이 아닌 "" 가 넘어오게 된다.
             * Items 객채안에 'List<Item> items' 로 정의 되어있기 때문에 에러가 발생한다.
             * 해서 String으로 값을 받아온 후 ObjectMapper로 수동적으로 데이터를 동기화한다.
             */
            String stringSyncCampInfo = this.getSyncCampInfo(numOfRows, pageNo, searchDate);
            OpenApiResponse syncCampInfo = this.mappingResponse(stringSyncCampInfo);

            if (isFirstIter) {
                totalCount = syncCampInfo.getResponse().getBody().getTotalCount();
                log.info("totalCount = {}", totalCount);

                int quotient = totalCount / numOfRows; //몫
                int remainder = totalCount % numOfRows; //나머지
                loopCount = remainder == 0 ? quotient : quotient + 1;

                //1.동기화 목록이 없으면 코드를 반복문을 수행할 필요가 없다.
                if (totalCount == 0) {
                    break;
                }

                //2.해당날짜에 변경 데이터가 100개 넘지 않으면 반복문을 수행할 필요가 없다.
                if (quotient == 0 && remainder != 0) {
                    boolLoop = false;
                }

                isFirstIter = false;
            }

            List<Item> items = syncCampInfo.getResponse().getBody().getItems().getItem();
            items.forEach(item -> {
                if ("A".equals(item.getSyncStatus())) {
                    itemMapDto.getNewCamps().add(item);
                } else {
                    itemMapDto.getUpdatedCamps().put(item.getContentId(), item);
                }
            });

            pageNo += 1;

            //3.페이지번호가 반복횟수보다 높으면 반복문을 수행할 필요가 없다.
            if (pageNo > loopCount) {
                boolLoop = false;
            }

        } while (boolLoop);

        return itemMapDto;
    }

    /**
     * OpenApi에서 날짜를 기준으로 변경사항이 있는 데이터를 가져오는 메서드
     */
    private String getSyncCampInfo(int numOfRows, int pageNo, String searchDate) {
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
                .bodyToMono(String.class)
                .block();
    }

    /**
     * OpenApi를 통해 가져온 데이터(String)을 객체에 매핑시켜주는 메서드
     */
    private OpenApiResponse mappingResponse(String stringSyncCampInfo) {

        //변경 데이터가 없을 경우 JSON 매핑을 위해 String 변경
        if (stringSyncCampInfo.contains("\"items\": \"\"")) {
            stringSyncCampInfo = stringSyncCampInfo.replace("\"items\": \"\"", "\"items\": {\"item\":[]}");
        }

        try {
            return objectMapper.readValue(stringSyncCampInfo, OpenApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new ExternalApiException(e, ExternalApiError.FAIL_JSON_MAPPING);
        }

    }

    /**
     * OpenApi에서 변경된 데이터를 데이터베이스에 동기화 시키는 메서드
     */
    private void updateCampInfo(Map<String, Item> updatedCamps) {

        //contentsId로 데이터베이스에서 업데이트 동기화에 해당되는 정보들을 조회한다.
        Set<String> updateCampsContentIds = updatedCamps.keySet();
        log.info(updateCampsContentIds.toString());

        List<Camp> campList = campRepository.findCampFetchAll(updateCampsContentIds);

        //가져온 정보들을 업데이트 한다.
        campList.forEach(camp -> {
            CampDetail campDetail = camp.getCampDetail();
            CampFacility campFacility = camp.getCampFacility();
            CampSite campSite = camp.getCampSite();

            String cpContentId = camp.getCpContentId();
            Item item = updatedCamps.get(cpContentId);

            camp.fromSyncOpenApiResponse(item);
            campDetail.fromSyncOpenApiResponse(camp, item);
            campFacility.fromSyncOpenApiResponse(camp, item);
            campSite.fromSyncOpenApiResponse(camp, item);

            camp.refCpdCpfCps(campDetail, campFacility, campSite);
        });
    }
}
