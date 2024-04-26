package com.project.camphub.exception.openapi;

public class OpenApiFetchException extends RuntimeException {

    public OpenApiFetchException() {
        super();
    }

    public OpenApiFetchException(String message) {
        super(message);
    }

    public OpenApiFetchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenApiFetchException(Throwable cause) {
        super(cause);
    }

    protected OpenApiFetchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
