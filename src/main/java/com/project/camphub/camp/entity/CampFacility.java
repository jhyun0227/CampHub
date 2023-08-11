package com.project.camphub.camp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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

}
