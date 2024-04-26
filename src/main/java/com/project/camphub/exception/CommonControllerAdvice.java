package com.project.camphub.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.project.camphub.common.dto.Response;
import com.project.camphub.common.dto.enumaration.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Response<Void>> invalidFormatException(InvalidFormatException e) {
        log.error("invalidFormatException 발생 = {}", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.exception(ResponseCode.CODE_500));
    }
}
