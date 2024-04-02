package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

@Getter
public enum AnimalEntryType {

    CAN("가능"),
    SMALL("가능(소형견)"),
    CANNOT("불가능");

    private final String description;
    AnimalEntryType(String description) {
        this.description = description;
    }

}
