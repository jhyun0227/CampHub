package com.project.camphub.camp.entity;

import com.project.camphub.externalapi.dto.openapi.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class CampDetail {

    @Id
    private String cpId;

    @MapsId //해당 엔터티의 주키를 현재 엔터티의 주키로 설정한다. 노션 정리2
    @JoinColumn(name = "cp_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Camp camp;

    @Column(length = 5000)
    private String cpdIntro;
    private String cpdLineIntro;
    @Column(length = 1000)
    private String cpdFeatureNm;
    @Column(length = 2000)
    private String cpdTooltip;
    @Column(length = 2000)
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
     * 데이터 저장
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

    /**
     * 데이터 수정
     * OpenApiResponse -> Camp
     */
    public void fromSyncOpenApiResponse(Camp camp, Item item) {
        this.camp = camp;
        this.cpdIntro = item.getIntro();
        this.cpdLineIntro = item.getLineIntro();
        this.cpdFeatureNm = item.getFeatureNm();
        this.cpdTooltip = item.getTooltip();
        this.cpdDirection = item.getDirection();
        this.cpdDoNm = item.getDoNm();
        this.cpdSigunguNm = item.getSigunguNm();
        this.cpdZipcode = item.getZipcode();
        this.cpdAddr1 = item.getAddr1();
        this.cpdAddr2 = item.getAddr2();
        this.cpdMapX = item.getMapX();
        this.cpdMapY = item.getMapY();
        this.cpdClturEventAt = item.getClturEventAt();
        this.cpdClturEvent = item.getClturEvent();
        this.cpdExprnProgrmAt = item.getExprnProgrmAt();
        this.cpdExprnProgrm = item.getExprnProgrm();
        this.cpdEqpmnLendCl = item.getEqpmnLendCl();
        this.cpdAnimalCmgCl = item.getAnimalCmgCl();
        this.cpdCaravAcmpnyAt = item.getCaravAcmpnyAt();
        this.cpdTrlerAcmpnyAt = item.getTrlerAcmpnyAt();
    }
}
