package com.project.camphub.exception;

import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.common.dto.enumaration.ResponseCode;
import com.project.camphub.exception.openapi.OpenApiFetchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OpenApiControllerAdvice {

    @ExceptionHandler(OpenApiFetchException.class)
    public ResponseEntity<ResponseDto<Void>> openApiFetchException(OpenApiFetchException e) {
        log.error("openApiFetchException = {}", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.exception(ResponseCode.CODE_500, "OpenApi 호출 예외 발생, 로그 확인"));
    }
}
