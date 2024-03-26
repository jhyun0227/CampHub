package com.project.camphub.domain.openapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/**
 * 코드화를 위한 임시 테이블,
 * 코드 정리 후 삭제 예정
 */
@Data
@Entity
public class TempCamp {

    @Id
    private String contentId;
    private String facltNm;
    private String tel;
    @Column(length = 500)
    private String homepage;
    private String resveCl;
    @Column(length = 2000)
    private String resveUrl;
    private String operPdCl;
    private String operDeCl;
    private String hvofBgnde;
    private String hvofEnddle;
    private String induty;
    private String lctCl;
    private String themaEnvrnCl;
    private String tourEraCl;
    @Column(length = 500)
    private String firstImageUrl;
    private String manageSttus;
    private String mangeDivNm;
    private String mgcDiv;
    private String facltDivNm;
    private String insrncAt;
    private String trsagntNo;
    private String bizrno;
    private String prmisnDe;
    private String createdtime;
    private String modifiedtime;
    private String syncStatus;
}
