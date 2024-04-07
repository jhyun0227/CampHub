package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.enumeration.BrazierType;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;

import static java.lang.Integer.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
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

    public static CampFacility apiToEntity(OpenApiResponse.Item item, Camp camp) {
        return CampFacility.builder()
                .camp(camp)
                .cpfTotalArea(parseInt(item.getAllar()))
                .cpfAmntyEtc(item.getSbrsEtc())
                .cpfNrbyFcltEtc(item.getPosblFcltyEtc())
                .cpfGnrlSiteCnt(parseInt(item.getGnrlSiteCo()))
                .cpfCarSiteCnt(parseInt(item.getAutoSiteCo()))
                .cpfGlampSiteCnt(parseInt(item.getGlampSiteCo()))
                .cpfCrvSiteCnt(parseInt(item.getCaravSiteCo()))
                .cpfPrvtCrvSiteCnt(parseInt(item.getIndvdlCaravSiteCo()))
                .cpfBrazierType(BrazierType.findByDescription(item.getBrazierCl()))
                .cpfSinkCnt(parseInt(item.getWtrplCo()))
                .cpfToiletCnt(parseInt(item.getToiletCo()))
                .cpfSwrmCnt(parseInt(item.getSwrmCo()))
                .cpfFireExtCnt(parseInt(item.getExtshrCo()))
                .cpfFireWaterCnt(parseInt(item.getFrprvtWrppCo()))
                .cpfFireSandCnt(parseInt(item.getFrprvtSandCo()))
                .cpfFireSensorCnt(parseInt(item.getFireSensorCo()))
                .build();
    }
}
