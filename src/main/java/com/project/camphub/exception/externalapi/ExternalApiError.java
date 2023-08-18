package com.project.camphub.exception.externalapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExternalApiError {

    FAIL_JSON_MAPPING(HttpStatus.INTERNAL_SERVER_ERROR, "500", "JSON 파싱 예외, OpenApiService.mappingResponse() 확인"),
    CAMP_SYNC_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "500", "CAMP 데이터 동기화 예외, OpenApiService.campSyncInfo() 확인");

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

}
