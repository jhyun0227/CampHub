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
public class CampFacility {

    @Id
    private String cpId;

    @MapsId(value = "cpId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @Column(length = 100)
    private String cpfTotalArea;
    @Column(length = 500)
    private String cpfAmntyEtc;
    @Column(length = 500)
    private String cpfNrbyFcltEtc;
    @Column(length = 10)
    private String cpfGnrlSiteCnt;
    @Column(length = 10)
    private String cpfCarSiteCnt;
    @Column(length = 10)
    private String cpfGlampSiteCnt;
    @Column(length = 10)
    private String cpfCrvSiteCnt;
    @Column(length = 10)
    private String cpfPrvtCrvSiteCnt;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private String cpfBrazierType;

    @Column(length = 10)
    private String cpfSinkCnt;
    @Column(length = 10)
    private String cpfToiletCnt;
    @Column(length = 10)
    private String cpfSwrmCnt;
    @Column(length = 10)
    private String cpfFireExtCnt;
    @Column(length = 10)
    private String cpfFireWaterCnt;
    @Column(length = 10)
    private String cpfFireSandCnt;
    @Column(length = 10)
    private String cpfFireSensorCnt;
}
