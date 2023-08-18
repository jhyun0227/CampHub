package com.project.camphub.camp.entity.enumcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CpManageDivNm {

    DIRECT_MANAGEMENT("직영", "M"),
    CONSIGNMENT("위탁", "C");

    private final String OpenApiValue;
    private final String DatabaseValue;

}
