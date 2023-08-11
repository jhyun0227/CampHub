package com.project.camphub.camp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
    @MapsId //해당 엔터티의 주키를 현재 엔터티의 주키로 설정한다. 노션 정리 2
    private Camp camp;

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
}
