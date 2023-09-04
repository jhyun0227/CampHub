package com.project.camphub.camp.dto;

import com.project.camphub.camp.entity.Camp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
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

    public static SearchCampListResponseDto fromEntity(Camp camp) {
        return SearchCampListResponseDto.builder()
                .cpId(camp.getCpId())
                .cpFirstImageUrl(camp.getCpFirstImageUrl())
                .cpTrsagntNo(camp.getCpTrsagntNo())
                .cpdDoNm(camp.getCampDetail().getCpdDoNm())
                .cpdSigunguNm(camp.getCampDetail().getCpdSigunguNm())
                .cpFacltNm(camp.getCpFacltNm())
                .cpdIntro(camp.getCampDetail().getCpdIntro())
                .cpdLineIntro(camp.getCampDetail().getCpdLineIntro())
                .cpdAddr1(camp.getCampDetail().getCpdAddr1())
                .cpdAddr2(camp.getCampDetail().getCpdAddr2())
                .cpTel(camp.getCpTel())
                .cpfSbrsCl(camp.getCampFacility().getCpfSbrsCl())
                .build();
    }
}