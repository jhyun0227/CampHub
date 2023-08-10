package com.project.camphub.externalapi.service;

import com.project.camphub.aop.annotation.OpenApiTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@Transactional
public class OpenApiService {

    private WebClient webClient;

    private final String endPoint; //Origin
    private final String basePath; //기본데이터 조회 Path
    private final String syncPath; //데이터 동기화 Path

    private final String encodingKey;
    private final String decodingKey;

    public OpenApiService(
            @Value("${openapi.endpoint}") String endPoint,
            @Value("${openapi.basepath}") String basePath,
            @Value("${openapi.syncpath}") String syncPath,
            @Value("${openapi.encoding}") String encodingKey,
            @Value("${openapi.decoding}") String decodingKey
    ) {
        this.endPoint = endPoint;
        this.basePath = basePath;
        this.syncPath = syncPath;
        this.encodingKey = encodingKey;
        this.decodingKey = decodingKey;
    }

    @PostConstruct
    private void setWebClient() {
        /**
         * WebClient의 인코딩방식이 UriComponentsBuilder#encode() 라는 옵션을 사용한다.
         * 이 옵션은 url의 예약 문자들을 치환하기 때문에, WebClient의 기본 인코딩으로 인해 Key 값이 변경되는 문제가 발생
         * url 인코딩 모드를 없애기 위해 DefaultUriBuilderFactory 클래스를 사용했다.
         */
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(endPoint);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        webClient = WebClient.builder().uriBuilderFactory(factory).build();
    }

    public String saveCampInfo() {
        return "ok";
    }

    @OpenApiTime
    public String getCampInfo() {

        String baseInfo = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(basePath)
//                        .queryParam("numOfRows", 2)
//                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "CampHub")
                        .queryParam("serviceKey", encodingKey)
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("baseInfo = {}", baseInfo);

        return "ok";
    }
}
