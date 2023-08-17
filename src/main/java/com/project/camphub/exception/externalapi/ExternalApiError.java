package com.project.camphub.exception.externalapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExternalApiError {

    FAIL_JSON_MAPPING(HttpStatus.INTERNAL_SERVER_ERROR, "500", "JSON 파싱 에러, OpenApiService.mappingResponse() 확인");

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

}
