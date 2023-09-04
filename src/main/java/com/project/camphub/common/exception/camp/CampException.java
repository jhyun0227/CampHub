package com.project.camphub.common.exception.camp;

import lombok.Getter;

@Getter
public class CampException extends RuntimeException {

    private CampError campError;

    public CampException(Throwable cause, CampError campError) {
        super(cause);
        this.campError = campError;
    }

}
