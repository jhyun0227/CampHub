package com.project.camphub.camp.dto;

import lombok.Data;

@Data
public class SearchCampRequestDto {

    private String facltNm;

    private Integer page;
    private Integer size;
}
