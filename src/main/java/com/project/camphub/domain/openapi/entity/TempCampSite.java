package com.project.camphub.domain.openapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 코드화를 위한 임시 테이블,
 * 코드 정리 후 삭제 예정
 */
@Data
@Entity
public class TempCampSite {

    @Id
    private String contentId;
    private String sitedStnc;
    private String siteMg1Co;
    private String siteMg2Co;
    private String siteMg3Co;
    private String siteMg1Width;
    private String siteMg1Vrticl;
    private String siteMg2Width;
    private String siteMg2Vrticl;
    private String siteMg3Width;
    private String siteMg3Vrticl;
    private String siteBottomCl1;
    private String siteBottomCl2;
    private String siteBottomCl3;
    private String siteBottomCl4;
    private String siteBottomCl5;

}
