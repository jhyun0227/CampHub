package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.enumeration.BrazierType;
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
    @Column(length = 10)
    private String cpId;

    @MapsId(value = "cpId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    private Integer cpfTotalArea;

    @Column(length = 300)
    private String cpfAmntyEtc;
    @Column(length = 300)
    private String cpfNrbyFcltEtc;

    private Integer cpfGnrlSiteCnt;
    private Integer cpfCarSiteCnt;
    private Integer cpfGlampSiteCnt;
    private Integer cpfCrvSiteCnt;
    private Integer cpfPrvtCrvSiteCnt;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private BrazierType cpfBrazierType;

    private Integer cpfSinkCnt;
    private Integer cpfToiletCnt;
    private Integer cpfSwrmCnt;
    private Integer cpfFireExtCnt;
    private Integer cpfFireWaterCnt;
    private Integer cpfFireSandCnt;
    private Integer cpfFireSensorCnt;
}
