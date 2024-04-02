package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

@Getter
public enum ManagingDivType {

    DIRECT("직영"),
    ENTRUST("위탁");

    private final String description;
    ManagingDivType(String description) {
        this.description = description;
    }

}
