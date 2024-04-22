package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.enumeration.BrazierType;
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
public class CampFacility implements Persistable<String> {

    @Id
    @Column(length = 10)
    private String cpId;

    @MapsId
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

    public static CampFacility apiToEntity(OpenApiResponse.Item item) {
        return CampFacility.builder()
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
                .isNew(true)
                .build();
    }

    public void updateCampFacility(OpenApiResponse.Item item) {
        this.cpfTotalArea = parseInt(item.getAllar());
        this.cpfAmntyEtc = item.getSbrsEtc();
        this.cpfNrbyFcltEtc = item.getPosblFcltyEtc();
        this.cpfGnrlSiteCnt = parseInt(item.getGnrlSiteCo());
        this.cpfCarSiteCnt = parseInt(item.getAutoSiteCo());
        this.cpfGlampSiteCnt = parseInt(item.getGlampSiteCo());
        this.cpfCrvSiteCnt = parseInt(item.getCaravSiteCo());
        this.cpfPrvtCrvSiteCnt = parseInt(item.getIndvdlCaravSiteCo());
        this.cpfBrazierType = BrazierType.findByDescription(item.getBrazierCl());
        this.cpfSinkCnt = parseInt(item.getWtrplCo());
        this.cpfToiletCnt = parseInt(item.getToiletCo());
        this.cpfSwrmCnt = parseInt(item.getSwrmCo());
        this.cpfFireExtCnt = parseInt(item.getExtshrCo());
        this.cpfFireWaterCnt = parseInt(item.getFrprvtWrppCo());
        this.cpfFireSandCnt = parseInt(item.getFrprvtSandCo());
        this.cpfFireSensorCnt = parseInt(item.getFireSensorCo());
    }

    public void linkToCamp(Camp camp) {
        this.camp = camp;
        camp.linkToCampFacility(this);
    }
}
