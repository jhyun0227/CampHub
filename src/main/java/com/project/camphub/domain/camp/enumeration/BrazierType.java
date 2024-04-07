package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum BrazierType {

    PRIVATE("개별"),
    PUBLIC("공동취사장"),
    CANNOT("불가");

    private final String description;
    BrazierType(String description) {
        this.description = description;
    }

    private static final Map<String, BrazierType> BRAZIER_TYPE_MAP = new HashMap<>();

    static {
        for (BrazierType brazierType : values()) {
            BRAZIER_TYPE_MAP.put(brazierType.getDescription(), brazierType);
        }
    }

    public static BrazierType findByDescription(String description) {
        return BRAZIER_TYPE_MAP.get(description);
    }
}
