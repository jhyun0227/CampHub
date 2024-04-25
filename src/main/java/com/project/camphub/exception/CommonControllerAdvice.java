package com.project.camphub.exception;

import com.project.camphub.domain.common.dto.Response;
import com.project.camphub.domain.common.enumaration.ResponseCode;
import com.project.camphub.exception.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Void>> notFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.exception(ResponseCode.CODE_404, e.getMessage()));
    }
}
