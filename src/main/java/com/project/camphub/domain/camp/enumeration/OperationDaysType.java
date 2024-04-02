package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

@Getter
public enum OperationDaysType {

    WEEKDAY("평일"),
    WEEKEND("주말"),
    ALWAYS("평일+주말");

    private final String description;
    OperationDaysType(String description) {
        this.description = description;
    }

}
