package com.project.camphub.camp.entity.enumcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CpOperDeCl {

    WEEKDAY("평일", "D"),
    WEEKEND("주말", "E"),
    ALL("평일+주말", "A");

    private String OpenApiValue;
    private String DatabaseValue;

}
