package com.project.camphub.camp.dto;

import com.project.camphub.camp.entity.Camp;
import com.project.camphub.camp.entity.CampFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Builder
public class CampFacilityDto {

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
     * Entity -> Dto
     */
    public static CampFacilityDto fromEntity(CampFacility campFacility) {
        return CampFacilityDto.builder()
                .cpfManageNmpr(campFacility.getCpfManageNmpr())
                .cpfAllar(campFacility.getCpfAllar())
                .cpfSbrsCl(campFacility.getCpfSbrsCl())
                .cpfSbrsEtc(campFacility.getCpfSbrsEtc())
                .cpfPosblFcltyCl(campFacility.getCpfPosblFcltyCl())
                .cpfPosblFcltyEtc(campFacility.getCpfPosblFcltyEtc())
                .cpfGnrlSiteCo(campFacility.getCpfGnrlSiteCo())
                .cpfAutoSiteCo(campFacility.getCpfAutoSiteCo())
                .cpfGlampSiteCo(campFacility.getCpfGlampSiteCo())
                .cpfCaravSiteCo(campFacility.getCpfCaravSiteCo())
                .cpfIndvdlCaravSiteCo(campFacility.getCpfIndvdlCaravSiteCo())
                .cpfGlampInnerFclty(campFacility.getCpfGlampInnerFclty())
                .cpfCaravInnerFclty(campFacility.getCpfCaravInnerFclty())
                .cpfBrazierCl(campFacility.getCpfBrazierCl())
                .cpfWtrplCo(campFacility.getCpfWtrplCo())
                .cpfToiletCo(campFacility.getCpfToiletCo())
                .cpfSwrmCo(campFacility.getCpfSwrmCo())
                .cpfExtshrCo(campFacility.getCpfExtshrCo())
                .cpfFrprvtWrppCo(campFacility.getCpfFrprvtWrppCo())
                .cpfFrprvtSandCo(campFacility.getCpfFrprvtSandCo())
                .cpfFireSensorCo(campFacility.getCpfFireSensorCo())
                .build();
    }

}
