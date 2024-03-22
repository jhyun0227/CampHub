package com.project.camphub.config.webclient;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertiesValue {

    //openApi
    private final String openApiEndPoint;
    private final String openApiBasePath;
    private final String openApiSyncPath;
    private final String openApiEncodingKey;
    private final String openApiDecodingKey;

    public PropertiesValue(
            @Value("${openapi.endpoint}") String openApiEndPoint,
            @Value("${openapi.basepath}") String openApiBasePath,
            @Value("${openapi.syncpath}") String openApiSyncPath,
            @Value("${openapi.encoding.key}") String openApiEncodingKey,
            @Value("${openapi.decoding.key}") String openApiDecodingKey) {
        this.openApiEndPoint = openApiEndPoint;
        this.openApiBasePath = openApiBasePath;
        this.openApiSyncPath = openApiSyncPath;
        this.openApiEncodingKey = openApiEncodingKey;
        this.openApiDecodingKey = openApiDecodingKey;
    }
}
