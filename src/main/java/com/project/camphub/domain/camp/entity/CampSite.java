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
    private String cpId;

    @MapsId(value = "cpId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @Column(length = 10)
    private String cpsSiteDist;
    @Column(length = 10)
    private String cpsSiteSize1Cnt;
    @Column(length = 10)
    private String cpsSiteSize2Cnt;
    @Column(length = 10)
    private String cpsSiteSize3Cnt;
    @Column(length = 10)
    private String cpsSiteSize1Width;
    @Column(length = 10)
    private String cpsSiteSize1Length;
    @Column(length = 10)
    private String cpsSiteSize2Width;
    @Column(length = 10)
    private String cpsSiteSize2Length;
    @Column(length = 10)
    private String cpsSiteSize3Width;
    @Column(length = 10)
    private String cpsSiteSize3Length;
    @Column(length = 10)
    private String cpsBttmGrassCnt;
    @Column(length = 10)
    private String cpsBttmStnCnt;
    @Column(length = 10)
    private String cpsBttmTechCnt;
    @Column(length = 10)
    private String cpsBttmGravelCnt;
    @Column(length = 10)
    private String cpsBttmDirtCnt;
}
