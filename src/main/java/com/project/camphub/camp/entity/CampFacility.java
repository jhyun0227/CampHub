package com.project.camphub.camp.entity;

import com.project.camphub.externalapi.dto.openapi.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CampFacility {

    @Id
    private String cpId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    @MapsId
    private Camp camp;

    private String cpfManageNmpr;
    private String cpfAllar;
    private String cpfSbrsCl;
    private String cpfSbrsEtc;
    private String cpfPosblFcltyCl;
    private String cpfPosblFcltyEtc;
    private String cpfGnrlSiteCo;
    private String cpfAutoSiteCo;
    private String cpfGlampSiteCo;
    private String cpfCaravSiteCo;
    private String cpfIndvdlCaravSiteCo;
    private String cpfGlampInnerFclty;
    private String cpfCaravInnerFclty;
    private String cpfBrazierCl;
    private String cpfWtrplCo;
    private String cpfToiletCo;
    private String cpfSwrmCo;
    private String cpfExtshrCo;
    private String cpfFrprvtWrppCo;
    private String cpfFrprvtSandCo;
    private String cpfFireSensorCo;

    /**
     * OpenApiResponse -> CampFacility
     */
    public static CampFacility fromOpenApiResponse(Camp camp, Item item) {
        return CampFacility.builder()
                .camp(camp)
                .cpfManageNmpr(item.getManageNmpr())
                .cpfAllar(item.getAllar())
                .cpfSbrsCl(item.getSbrsCl())
                .cpfSbrsEtc(item.getSbrsEtc())
                .cpfPosblFcltyCl(item.getPosblFcltyCl())
                .cpfPosblFcltyEtc(item.getPosblFcltyEtc())
                .cpfGnrlSiteCo(item.getGnrlSiteCo())
                .cpfAutoSiteCo(item.getAutoSiteCo())
                .cpfGlampSiteCo(item.getGlampSiteCo())
                .cpfCaravSiteCo(item.getCaravSiteCo())
                .cpfIndvdlCaravSiteCo(item.getIndvdlCaravSiteCo())
                .cpfGlampInnerFclty(item.getGlampInnerFclty())
                .cpfCaravInnerFclty(item.getCaravInnerFclty())
                .cpfBrazierCl(item.getBrazierCl())
                .cpfWtrplCo(item.getWtrplCo())
                .cpfToiletCo(item.getToiletCo())
                .cpfSwrmCo(item.getSwrmCo())
                .cpfExtshrCo(item.getExtshrCo())
                .cpfFrprvtWrppCo(item.getFrprvtWrppCo())
                .cpfFrprvtSandCo(item.getFrprvtSandCo())
                .cpfFireSensorCo(item.getFireSensorCo())
                .build();
    }

}
