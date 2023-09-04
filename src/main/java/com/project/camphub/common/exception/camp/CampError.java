package com.project.camphub.common.exception.camp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CampError {

    NOT_EXIST_CAMP(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 캠프ID 입니다.");

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

}
