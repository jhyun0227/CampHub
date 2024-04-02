package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

@Getter
public enum ManagingStatusType {

    OPERATE("운영"),
    CLOSED("휴장"),
    SHUTDOWN("폐업");

    private final String description;
    ManagingStatusType(String description) {
        this.description = description;
    }

}
