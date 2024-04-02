package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

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

}
