package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import static java.lang.Integer.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@ToString
public class CampSite implements Persistable<String> {

    @Id
    @Column(length = 10)
    private String cpId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    private Integer cpsSiteDist;

    @Column(name = "cps_site_size1_cnt")
    private Integer cpsSiteSize1Cnt;
    @Column(name = "cps_site_size1_width")
    private Integer cpsSiteSize1Width;
    @Column(name = "cps_site_size1_length")
    private Integer cpsSiteSize1Length;
    @Column(name = "cps_site_size2_cnt")
    private Integer cpsSiteSize2Cnt;
    @Column(name = "cps_site_size2_width")
    private Integer cpsSiteSize2Width;
    @Column(name = "cps_site_size2_length")
    private Integer cpsSiteSize2Length;
    @Column(name = "cps_site_size3_cnt")
    private Integer cpsSiteSize3Cnt;
    @Column(name = "cps_site_size3_width")
    private Integer cpsSiteSize3Width;
    @Column(name = "cps_site_size3_length")
    private Integer cpsSiteSize3Length;

    private Integer cpsBttmGrassCnt;
    private Integer cpsBttmStnCnt;
    private Integer cpsBttmTechCnt;
    private Integer cpsBttmGravelCnt;
    private Integer cpsBttmDirtCnt;

    @Transient
    private boolean isNew = false;

    @Override
    public String getId() {
        return this.getCpId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public static CampSite apiToEntity(OpenApiResponse.Item item) {
        return CampSite.builder()
                .cpsSiteDist(parseInt(item.getSitedStnc()))
                .cpsSiteSize1Cnt(parseInt(item.getSiteMg1Co()))
                .cpsSiteSize1Width(parseInt(item.getSiteMg1Width()))
                .cpsSiteSize1Length(parseInt(item.getSiteMg1Vrticl()))
                .cpsSiteSize2Cnt(parseInt(item.getSiteMg2Co()))
                .cpsSiteSize2Width(parseInt(item.getSiteMg2Width()))
                .cpsSiteSize2Length(parseInt(item.getSiteMg2Vrticl()))
                .cpsSiteSize3Cnt(parseInt(item.getSiteMg3Co()))
                .cpsSiteSize3Width(parseInt(item.getSiteMg3Width()))
                .cpsSiteSize3Length(parseInt(item.getSiteMg3Vrticl()))
                .cpsBttmGrassCnt(parseInt(item.getSiteBottomCl1()))
                .cpsBttmStnCnt(parseInt(item.getSiteBottomCl2()))
                .cpsBttmTechCnt(parseInt(item.getSiteBottomCl3()))
                .cpsBttmGravelCnt(parseInt(item.getSiteBottomCl4()))
                .cpsBttmDirtCnt(parseInt(item.getSiteBottomCl5()))
                .isNew(true)
                .build();
    }

    public void updateCampSite(OpenApiResponse.Item item) {
        this.cpsSiteDist = parseInt(item.getSitedStnc());
        this.cpsSiteSize1Cnt = parseInt(item.getSiteMg1Co());
        this.cpsSiteSize1Width = parseInt(item.getSiteMg1Width());
        this.cpsSiteSize1Length = parseInt(item.getSiteMg1Vrticl());
        this.cpsSiteSize2Cnt = parseInt(item.getSiteMg2Co());
        this.cpsSiteSize2Width = parseInt(item.getSiteMg2Width());
        this.cpsSiteSize2Length = parseInt(item.getSiteMg2Vrticl());
        this.cpsSiteSize3Cnt = parseInt(item.getSiteMg3Co());
        this.cpsSiteSize3Width = parseInt(item.getSiteMg3Width());
        this.cpsSiteSize3Length = parseInt(item.getSiteMg3Vrticl());
        this.cpsBttmGrassCnt = parseInt(item.getSiteBottomCl1());
        this.cpsBttmStnCnt = parseInt(item.getSiteBottomCl2());
        this.cpsBttmTechCnt = parseInt(item.getSiteBottomCl3());
        this.cpsBttmGravelCnt = parseInt(item.getSiteBottomCl4());
        this.cpsBttmDirtCnt = parseInt(item.getSiteBottomCl5());
    }

    public void linkToCamp(Camp camp) {
        this.camp = camp;
        camp.linkToCampSite(this);
    }
}
