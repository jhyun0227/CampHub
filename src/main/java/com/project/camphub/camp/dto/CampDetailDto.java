package com.project.camphub.camp.dto;

import com.project.camphub.camp.entity.Camp;
import com.project.camphub.camp.entity.CampDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Builder
public class CampDetailDto {
    
    private String cpdIntro;
    private String cpdLineIntro;
    private String cpdFeatureNm;
    private String cpdTooltip;
    private String cpdDirection;
    private String cpdDoNm;
    private String cpdSigunguNm;
    private String cpdZipcode;
    private String cpdAddr1;
    private String cpdAddr2;
    private String cpdMapX;
    private String cpdMapY;
    private String cpdClturEventAt;
    private String cpdClturEvent;
    private String cpdExprnProgrmAt;
    private String cpdExprnProgrm;
    private String cpdEqpmnLendCl;
    private String cpdAnimalCmgCl;
    private String cpdCaravAcmpnyAt;
    private String cpdTrlerAcmpnyAt;

    /**
     * Entity -> Dto
     */
    public static CampDetailDto fromEntity(CampDetail campDetail) {
        return CampDetailDto.builder()
                .cpdIntro(campDetail.getCpdIntro())
                .cpdLineIntro(campDetail.getCpdLineIntro())
                .cpdFeatureNm(campDetail.getCpdFeatureNm())
                .cpdTooltip(campDetail.getCpdTooltip())
                .cpdDirection(campDetail.getCpdDirection())
                .cpdDoNm(campDetail.getCpdDoNm())
                .cpdSigunguNm(campDetail.getCpdSigunguNm())
                .cpdZipcode(campDetail.getCpdZipcode())
                .cpdAddr1(campDetail.getCpdAddr1())
                .cpdAddr2(campDetail.getCpdAddr2())
                .cpdMapX(campDetail.getCpdMapX())
                .cpdMapY(campDetail.getCpdMapY())
                .cpdClturEventAt(campDetail.getCpdClturEventAt())
                .cpdClturEvent(campDetail.getCpdClturEvent())
                .cpdExprnProgrmAt(campDetail.getCpdExprnProgrmAt())
                .cpdExprnProgrm(campDetail.getCpdExprnProgrm())
                .cpdEqpmnLendCl(campDetail.getCpdEqpmnLendCl())
                .cpdAnimalCmgCl(campDetail.getCpdAnimalCmgCl())
                .cpdCaravAcmpnyAt(campDetail.getCpdCaravAcmpnyAt())
                .cpdTrlerAcmpnyAt(campDetail.getCpdTrlerAcmpnyAt())
                .build();
    }
}
