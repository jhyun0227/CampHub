package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum OperationDaysType {

    WEEKDAY("평일"),
    WEEKEND("주말"),
    ALWAYS("평일+주말");

    private final String description;
    OperationDaysType(String description) {
        this.description = description;
    }

    public static final Map<String, OperationDaysType> OPERATION_DAYS_TYPE_MAP = new HashMap<>();

    static {
        for (OperationDaysType operationDaysType : values()) {
            OPERATION_DAYS_TYPE_MAP.put(operationDaysType.getDescription(), operationDaysType);
        }
    }

    public static OperationDaysType findByDescription(String description) {
        return OPERATION_DAYS_TYPE_MAP.get(description);
    }
}
