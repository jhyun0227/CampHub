package com.project.camphub.common.exception.externalapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExternalApiError {

    CAMP_INFO_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "500", "OpenApi -> DB 데이터 가져오기 예외, OpenApiService.campInfo() 확인"),
    CAMP_SYNC_INFO_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "500", "OpenApi -> DB 데이터 동기화 예외, OpenApiService.campSyncInfo() 확인");

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

}
