package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum FacilityDivType {

    PRIVATE("민간"),
    GOVT("지자체"),
    PARK("국립공원"),
    LEISURE("국민여가"),
    FOREST("자연휴양림");

    private final String description;
    FacilityDivType(String description) {
        this.description = description;
    }

    public static final Map<String, FacilityDivType> FACILITY_DIV_TYPE_MAP = new HashMap<>();

    static {
        for (FacilityDivType facilityDivType : values()) {
            FACILITY_DIV_TYPE_MAP.put(facilityDivType.getDescription(), facilityDivType);
        }
    }

    public static FacilityDivType findByDescription(String description) {
        return FACILITY_DIV_TYPE_MAP.get(description);
    }

}
