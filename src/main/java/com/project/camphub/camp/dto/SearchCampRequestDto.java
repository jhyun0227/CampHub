package com.project.camphub.camp.dto;

import lombok.Data;

@Data
public class SearchCampRequestDto {

    private String facltNm;

    private String doCd;
    private String doNm;
    private String sigunguCd;
    private String sigunguNm;

    private Integer page;
    private Integer size;
}
