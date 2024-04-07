package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class CampSite {

    @Id
    @Column(length = 10)
    private String cpId;

    @MapsId(value = "cpId")
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

    public static CampSite apiToEntity(OpenApiResponse.Item item, Camp camp) {
        return CampSite.builder()
                .camp(camp)
                .cpsSiteDist(Integer.parseInt(item.getSitedStnc()))
                .cpsSiteSize1Cnt(Integer.parseInt(item.getSiteMg1Co()))
                .cpsSiteSize1Width(Integer.parseInt(item.getSiteMg1Width()))
                .cpsSiteSize1Length(Integer.parseInt(item.getSiteMg1Vrticl()))
                .cpsSiteSize2Cnt(Integer.parseInt(item.getSiteMg2Co()))
                .cpsSiteSize2Width(Integer.parseInt(item.getSiteMg2Width()))
                .cpsSiteSize2Length(Integer.parseInt(item.getSiteMg2Vrticl()))
                .cpsSiteSize3Cnt(Integer.parseInt(item.getSiteMg3Co()))
                .cpsSiteSize3Width(Integer.parseInt(item.getSiteMg3Width()))
                .cpsSiteSize3Length(Integer.parseInt(item.getSiteMg3Vrticl()))
                .cpsBttmGrassCnt(Integer.parseInt(item.getSiteBottomCl1()))
                .cpsBttmStnCnt(Integer.parseInt(item.getSiteBottomCl2()))
                .cpsBttmTechCnt(Integer.parseInt(item.getSiteBottomCl3()))
                .cpsBttmGravelCnt(Integer.parseInt(item.getSiteBottomCl4()))
                .cpsBttmDirtCnt(Integer.parseInt(item.getSiteBottomCl5()))
                .build();
    }
}
