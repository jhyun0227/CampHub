package com.project.camphub.camp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CampSite {

    @Id
    private String cpId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    @MapsId
    private Camp camp;

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
