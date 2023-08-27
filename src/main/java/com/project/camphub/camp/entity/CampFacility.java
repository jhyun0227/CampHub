package com.project.camphub.camp.entity;

import com.project.camphub.externalapi.dto.openapi.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = "camp")
public class CampFacility {

    @Id
    private String cpId;

    @MapsId //해당 엔터티의 주키를 현재 엔터티의 주키로 설정한다. 노션 정리2
    @JoinColumn(name = "cp_id")
    @OneToOne(fetch = FetchType.LAZY)
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
     * 데이터 저장
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

    /**
     * 데이터 수정
     * OpenApiResponse -> Camp
     */
    public void fromSyncOpenApiResponse(Camp camp, Item item) {
        this.camp = camp;
        this.cpfManageNmpr = item.getManageNmpr();
        this.cpfAllar = item.getAllar();
        this.cpfSbrsCl = item.getSbrsCl();
        this.cpfSbrsEtc = item.getSbrsEtc();
        this.cpfPosblFcltyCl = item.getPosblFcltyCl();
        this.cpfPosblFcltyEtc = item.getPosblFcltyEtc();
        this.cpfGnrlSiteCo = item.getGnrlSiteCo();
        this.cpfAutoSiteCo = item.getAutoSiteCo();
        this.cpfGlampSiteCo = item.getGlampSiteCo();
        this.cpfCaravSiteCo = item.getCaravSiteCo();
        this.cpfIndvdlCaravSiteCo = item.getIndvdlCaravSiteCo();
        this.cpfGlampInnerFclty = item.getGlampInnerFclty();
        this.cpfCaravInnerFclty = item.getCaravInnerFclty();
        this.cpfBrazierCl = item.getBrazierCl();
        this.cpfWtrplCo = item.getWtrplCo();
        this.cpfToiletCo = item.getToiletCo();
        this.cpfSwrmCo = item.getSwrmCo();
        this.cpfExtshrCo = item.getExtshrCo();
        this.cpfFrprvtWrppCo = item.getFrprvtWrppCo();
        this.cpfFrprvtSandCo = item.getFrprvtSandCo();
        this.cpfFireSensorCo = item.getFireSensorCo();
    }
}
