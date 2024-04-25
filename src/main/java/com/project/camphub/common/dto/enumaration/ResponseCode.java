package com.project.camphub.common.dto.enumaration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    CODE_200(200, HttpStatus.OK, "정상적으로 처리되었습니다."),
    CODE_201(201, HttpStatus.CREATED,  "정상적으로 처리되었습니다."),
    CODE_202(202, HttpStatus.ACCEPTED,  "정상적으로 처리되었습니다."),
    CODE_400(400, HttpStatus.BAD_REQUEST,  "요청 양식이 올바르지 않습니다."),
    CODE_401(401, HttpStatus.UNAUTHORIZED,  "인증 정보가 누락되었습니다."),
    CODE_403(403, HttpStatus.FORBIDDEN,  "접근 권한이 없습니다."),
    CODE_404(404, HttpStatus.NOT_FOUND,  "요청을 찾을 수 없습니다."),
    CODE_405(405, HttpStatus.METHOD_NOT_ALLOWED,  "허용되지 않은 요청 방식입니다."),
    CODE_500(500, HttpStatus.INTERNAL_SERVER_ERROR,  "서버에서 요청을 처리 중 오류가 발생했습니다.");

    private int code;
    private HttpStatus status;
    private String message;
}
