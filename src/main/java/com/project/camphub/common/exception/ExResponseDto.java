package com.project.camphub.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ExResponseDto {

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

}
