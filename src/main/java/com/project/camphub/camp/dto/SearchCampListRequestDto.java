package com.project.camphub.camp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchCampListRequestDto {

    //야영장명
    private String facltNm;
    //도
    private List<String> doCdList = new ArrayList<>();
    private List<String> doNmList = new ArrayList<>();
    //시군구
    private String sigunguCd;
    private String sigunguNm;
    //입지구분(자연환경)
    private List<String> lctClCdList = new ArrayList<>();
    private List<String> lctClNmList = new ArrayList<>();
    //사업주체(운영형태)
    private List<String> facltDivCdList = new ArrayList<>();
    private List<String> facltDivNmList = new ArrayList<>();
    //업종(주요시설)
    private List<String> indutyCdList = new ArrayList<>();
    private List<String> indutyNmList = new ArrayList<>();
    //사이트바닥
    private List<String> siteBottomCdList = new ArrayList<>();
    //테마환경
    private List<String> themaEnvironmentCdList = new ArrayList<>();
    private List<String> themaEnvironmentNmList = new ArrayList<>();
    //부대시설
    private List<String> facilityCdList = new ArrayList<>();
    private List<String> facilityNmList = new ArrayList<>();

    private Integer page;
    private Integer size;
}
