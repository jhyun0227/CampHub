package com.project.camphub.exception.externalapi;

import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException {

    private ExternalApiError externalApiError;

    public ExternalApiException(Throwable cause, ExternalApiError externalApiError) {
        super(cause);
        this.externalApiError = externalApiError;
    }

}
