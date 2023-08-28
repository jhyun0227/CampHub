package com.project.camphub.camp.dto;

import lombok.Data;

@Data
public class SearchCampListRequestDto {

    //야영장명
    private String facltNm;
    //도
    private String doCd;
    private String doNm;
    //시군구
    private String sigunguCd;
    private String sigunguNm;
    //입지구분(자연환경)
    private String lctClCd;
    private String lctClNm;
    //사업주체(운영형태)
    private String facltDivCd;
    private String facltDivNm;
    //업종
    private String indutyCd;
    private String indutyNm;

    private Integer page;
    private Integer size;
}
