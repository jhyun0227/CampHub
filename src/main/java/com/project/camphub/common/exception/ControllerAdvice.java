package com.project.camphub.common.exception;

import com.project.camphub.common.exception.camp.CampError;
import com.project.camphub.common.exception.camp.CampException;
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

        log.info("### ExternalApiException 발생, message = {}", e.getExternalApiError().getErrorMessage(), e.getCause());

        ExternalApiError externalApiError = e.getExternalApiError();
        ExResponseDto exResponseDto =
                new ExResponseDto(externalApiError.getHttpStatus(), externalApiError.getErrorCode(), externalApiError.getErrorMessage());

        return ResponseEntity.status(e.getExternalApiError().getHttpStatus()).body(exResponseDto);
    }

    @ExceptionHandler(CampException.class)
    public ResponseEntity<ExResponseDto> campException(CampException e) {
        log.info("### CampException 발생, message = {}", e.getCampError().getErrorMessage());

        CampError campError = e.getCampError();
        ExResponseDto exResponseDto
                = new ExResponseDto(campError.getHttpStatus(), campError.getErrorCode(), campError.getErrorMessage());

        return ResponseEntity.status(e.getCampError().getHttpStatus()).body(exResponseDto);
    }

}
