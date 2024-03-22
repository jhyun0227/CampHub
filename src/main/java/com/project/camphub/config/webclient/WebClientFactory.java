package com.project.camphub.config.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * 스프링 빈으로 등록...?
 * openApi만 호출하면 빈으로 등록하는게 낫지만, 추후 다른 api 생성 가능성 있음...
 */
public class WebClientFactory {

    public static WebClient createWebClient(String endPoint) {
        //url 예약 문자 치환 문제로 인해 설정
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(endPoint);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .build();
    }
}
