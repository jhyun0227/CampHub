package com.project.camphub.exception;

import com.project.camphub.common.dto.Response;
import com.project.camphub.common.dto.enumaration.ResponseCode;
import com.project.camphub.exception.camp.CampNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CampControllerAdvice {

    @ExceptionHandler(CampNotFoundException.class)
    public ResponseEntity<Response<Void>> campNotFoundException(CampNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.exception(ResponseCode.CODE_404, e.getMessage()));
    }
}
