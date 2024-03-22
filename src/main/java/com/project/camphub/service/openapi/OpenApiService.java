package com.project.camphub.service.openapi;

import com.project.camphub.config.webclient.PropertiesValue;
import com.project.camphub.config.webclient.WebClientFactory;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final PropertiesValue propertiesValue;

    public void fetchAndInsertCampList() {
        fetchCampList();
    }

    private void fetchCampList() {
        WebClient webClient = WebClientFactory.createWebClient(propertiesValue.getOpenApiEndPoint());

        OpenApiResponse openApiResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(propertiesValue.getOpenApiSyncPath())
                        .queryParam("numOfRows", 100)
                        .queryParam("pageNo", 1)
                        .queryParam("MobileOS", "ETC")
                        .queryParam("MobileApp", "CampHub")
                        .queryParam("serviceKey", propertiesValue.getOpenApiEncodingKey())
                        .queryParam("_type", "json")
                        .build()
                )
                .retrieve()
                .bodyToMono(OpenApiResponse.class)
                .block();


        log.info("openApiResponse = {}", openApiResponse);
    }
}
