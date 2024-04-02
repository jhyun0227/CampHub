package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

@Getter
public enum BrazierType {

    PRIVATE("개별"),
    PUBLIC("공동취사장"),
    CANNOT("불가");

    private final String description;
    BrazierType(String description) {
        this.description = description;
    }

}
