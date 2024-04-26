package com.project.camphub.exception.camp;

import com.project.camphub.exception.common.NotFoundException;

public class CampNotFoundException extends NotFoundException {
    public CampNotFoundException() {
        super();
    }

    public CampNotFoundException(String message) {
        super(message);
    }

    public CampNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CampNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
