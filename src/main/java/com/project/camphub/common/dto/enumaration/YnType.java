package com.project.camphub.common.dto.enumaration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum YnType {
    Y("Y"),
    N("N");

    private String description;

    YnType(String description) {
        this.description = description;
    }

    private static final Map<String, YnType> YN_TYPE_MAP = new HashMap<>();

    static {
        for (YnType ynType : values()) {
            YN_TYPE_MAP.put(ynType.getDescription(), ynType);
        }
    }

    public static YnType findByDescription(String description) {
        return YN_TYPE_MAP.get(description);
    }
}
