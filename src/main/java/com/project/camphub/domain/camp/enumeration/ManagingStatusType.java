package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ManagingStatusType {

    OPERATE("운영"),
    CLOSED("휴장"),
    SHUTDOWN("폐업");

    private final String description;
    ManagingStatusType(String description) {
        this.description = description;
    }

    public static final Map<String, ManagingStatusType> MANAGING_STATUS_TYPE_MAP = new HashMap<>();

    static {
        for (ManagingStatusType managingStatusType : values()) {
            MANAGING_STATUS_TYPE_MAP.put(managingStatusType.getDescription(), managingStatusType);
        }
    }

    public static ManagingStatusType findByDescription(String description) {
        return MANAGING_STATUS_TYPE_MAP.get(description);
    }
}
