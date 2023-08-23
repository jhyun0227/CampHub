package com.project.camphub.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseDto {

    private HttpStatus httpStatus;
    private Object data;
    private String message;


}
