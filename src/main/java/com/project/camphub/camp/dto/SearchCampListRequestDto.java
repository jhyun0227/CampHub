package com.project.camphub.camp.dto;

import lombok.Data;

@Data
public class SearchCampListRequestDto {

    private String facltNm;

    private String doCd;
    private String doNm;
    private String sigunguCd;
    private String sigunguNm;
    private String lctClCd;
    private String lctClNm;

    private Integer page;
    private Integer size;
}
