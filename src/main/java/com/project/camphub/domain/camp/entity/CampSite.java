package com.project.camphub.domain.camp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampSite {

    @Id
    @Column(length = 10)
    private String cpId;

    @MapsId(value = "cpId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    private Integer cpsSiteDist;
    private Integer cpsSiteSize1Cnt;
    private Integer cpsSiteSize2Cnt;
    private Integer cpsSiteSize3Cnt;
    private Integer cpsSiteSize1Width;
    private Integer cpsSiteSize1Length;
    private Integer cpsSiteSize2Width;
    private Integer cpsSiteSize2Length;
    private Integer cpsSiteSize3Width;
    private Integer cpsSiteSize3Length;
    private Integer cpsBttmGrassCnt;
    private Integer cpsBttmStnCnt;
    private Integer cpsBttmTechCnt;
    private Integer cpsBttmGravelCnt;
    private Integer cpsBttmDirtCnt;
}
