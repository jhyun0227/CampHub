package com.project.camphub.domain.common.dto;

import com.project.camphub.domain.common.enumaration.ResponseCode;

public record Response<T>(int code, String message, T data) {

    public static <T> Response<T> success(ResponseCode responseCode, T data) {
        return new Response<>(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static Response<Void> exception(ResponseCode responseCode, String message) {
        return new Response<>(responseCode.getCode(), message, null);
    }
}
