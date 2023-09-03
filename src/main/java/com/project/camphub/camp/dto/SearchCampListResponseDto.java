package com.project.camphub.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCampListResponseDto {

    private String cpId;
    private String cpFirstImageUrl;
    private String cpTrsagntNo;
    private String cpdDoNm;
    private String cpdSigunguNm;
    private String cpFacltNm;
    private String cpdIntro;
    private String cpdLineIntro;
    private String cpdAddr1;
    private String cpdAddr2;
    private String cpTel;
    private String cpfSbrsCl;

}