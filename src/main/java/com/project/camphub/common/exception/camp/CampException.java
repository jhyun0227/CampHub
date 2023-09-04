package com.project.camphub.common.exception.camp;

import lombok.Getter;

@Getter
public class CampException extends RuntimeException {

    private CampError campError;

    public CampException(CampError campError) {
        this.campError = campError;
    }

}
