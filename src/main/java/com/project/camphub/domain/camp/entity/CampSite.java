package com.project.camphub.domain.camp.entity;

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
}
