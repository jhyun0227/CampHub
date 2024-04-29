package com.project.camphub.common.dto;

import com.project.camphub.common.dto.enumaration.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ResponseDto", description = "공통 응답 DTO")
public record ResponseDto<T>(
        @Schema(description = "응답 록코드")
        int code,
        @Schema(description = "응답 메세지")
        String message,
        @Schema(description = "응답 데이터")
        T data) {

    public static <T> ResponseDto<T> success(ResponseCode responseCode, T data) {
        return new ResponseDto<>(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> ResponseDto<T> success(ResponseCode responseCode, String message, T data) {
        return new ResponseDto<>(responseCode.getCode(), message, data);
    }

    public static ResponseDto<Void> exception(ResponseCode responseCode) {
        return new ResponseDto<>(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static ResponseDto<Void> exception(ResponseCode responseCode, String message) {
        return new ResponseDto<>(responseCode.getCode(), message, null);
    }
}
