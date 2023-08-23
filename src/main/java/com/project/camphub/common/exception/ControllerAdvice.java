package com.project.camphub.common.exception;

import com.project.camphub.common.exception.externalapi.ExternalApiError;
import com.project.camphub.common.exception.externalapi.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ExResponseDto> externalApiException(ExternalApiException e) {

        log.info("### ExternalApiExceptiond 예외 발생, Throwable = {}", e.getCause());

        ExternalApiError externalApiError = e.getExternalApiError();
        ExResponseDto exResponseDto =
                new ExResponseDto(externalApiError.getHttpStatus(), externalApiError.getErrorCode(), externalApiError.getErrorMessage());

        return ResponseEntity.status(e.getExternalApiError().getHttpStatus()).body(exResponseDto);
    }

}
