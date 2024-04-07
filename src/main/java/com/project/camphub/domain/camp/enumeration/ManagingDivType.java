package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ManagingDivType {

    DIRECT("직영"),
    ENTRUST("위탁");

    private final String description;
    ManagingDivType(String description) {
        this.description = description;
    }

    private static final Map<String, ManagingDivType> MANAGING_DIV_TYPE_MAP = new HashMap<>();

    static {
        for (ManagingDivType managingDivType :  values()) {
            MANAGING_DIV_TYPE_MAP.put(managingDivType.getDescription(), managingDivType);
        }
    }

    public static ManagingDivType findByDescription(String description) {
        return MANAGING_DIV_TYPE_MAP.get(description);
    }

}
