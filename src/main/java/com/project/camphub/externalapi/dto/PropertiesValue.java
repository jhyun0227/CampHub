package com.project.camphub.externalapi.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertiesValue {

    //OpenApi
    private final String endPoint; //Origin
    private final String basePath; //기본데이터 조회 Path
    private final String syncPath; //데이터 동기화 Path
    private final String encodingKey;
    private final String decodingKey;

    public PropertiesValue(
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
}
