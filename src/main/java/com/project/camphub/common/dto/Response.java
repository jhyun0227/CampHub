package com.project.camphub.common.dto;

import com.project.camphub.common.dto.enumaration.ResponseCode;

public record Response<T>(int code, String message, T data) {

    public static <T> Response<T> success(ResponseCode responseCode, T data) {
        return new Response<>(responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> Response<T> success(ResponseCode responseCode, String message, T data) {
        return new Response<>(responseCode.getCode(), message, data);
    }

    public static Response<Void> exception(ResponseCode responseCode) {
        return new Response<>(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static Response<Void> exception(ResponseCode responseCode, String message) {
        return new Response<>(responseCode.getCode(), message, null);
    }
}
