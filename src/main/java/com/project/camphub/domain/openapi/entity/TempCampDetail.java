package com.project.camphub.domain.openapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 코드화를 위한 임시 테이블,
 * 코드 정리 후 삭제 예정
 */
@Data
@Entity
public class TempCampDetail {

    @Id
    private String contentId;
    @Column(length = 5000)
    private String intro;
    private String lineIntro;
    @Column(length = 2000)
    private String featureNm;
    @Column(length = 2000)
    private String tooltip;
    private String induty;
    private String lctCl;
    private String themaEnvrnCl;
    private String tourEraCl;
    private String operPdCl;
    private String operDeCl;
    private String manageSttus;
    private String mangeDivNm;
    private String mgcDiv;
    private String facltDivNm;
    private String manageNmpr;
    private String hvofBgnde;
    private String hvofEnddle;
    private String clturEventAt;
    private String clturEvent;
    private String exprnProgrmAt;
    private String exprnProgrm;
    private String eqpmnLendCl;
    private String animalCmgCl;
    private String caravAcmpnyAt;
    private String trlerAcmpnyAt;
    private String insrncAt;
}
