package com.project.camphub.camp.entity.enumcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CpManageSttus {

    OPERATE("운영", "O"),
    TEMPORARY_CLOSED("휴장", "C"),
    OUT_OF_BUSINESS("폐업", "D");

    private final String OpenApiValue;
    private final String DatabaseValue;

}
