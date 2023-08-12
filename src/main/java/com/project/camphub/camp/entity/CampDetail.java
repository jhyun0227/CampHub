package com.project.camphub.camp.entity;

import com.project.camphub.externalapi.dto.openapi.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CampDetail {

    @Id
    private String cpId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    @MapsId //해당 엔터티의 주키를 현재 엔터티의 주키로 설정한다. 노션 정리2
    private Camp camp;

    @Column(length = 2000)
    private String cpdIntro;
    private String cpdLineIntro;
    @Column(length = 1000)
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
     * OpenApiResponse -> CampDetail
     */
    public static CampDetail fromOpenApiResponse(Camp camp, Item item) {
        return CampDetail.builder()
                .camp(camp)
                .cpdIntro(item.getIntro())
                .cpdLineIntro(item.getLineIntro())
                .cpdFeatureNm(item.getFeatureNm())
                .cpdTooltip(item.getTooltip())
                .cpdDirection(item.getDirection())
                .cpdDoNm(item.getDoNm())
                .cpdSigunguNm(item.getSigunguNm())
                .cpdZipcode(item.getZipcode())
                .cpdAddr1(item.getAddr1())
                .cpdAddr2(item.getAddr2())
                .cpdMapX(item.getMapX())
                .cpdMapY(item.getMapY())
                .cpdClturEventAt(item.getClturEventAt())
                .cpdClturEvent(item.getClturEvent())
                .cpdExprnProgrmAt(item.getExprnProgrmAt())
                .cpdExprnProgrm(item.getExprnProgrm())
                .cpdEqpmnLendCl(item.getEqpmnLendCl())
                .cpdAnimalCmgCl(item.getAnimalCmgCl())
                .cpdCaravAcmpnyAt(item.getCaravAcmpnyAt())
                .cpdTrlerAcmpnyAt(item.getTrlerAcmpnyAt())
                .build();
    }
}
