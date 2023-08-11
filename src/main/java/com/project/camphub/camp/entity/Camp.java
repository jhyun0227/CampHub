package com.project.camphub.camp.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Camp {

    @Id
    private String cpId;
    private String cpContentId;
    private String cpFacltNm;
    private String cpTel;
    private String cpHomepage;
    private String cpResveCl;
    private String cpResveUrl;
    private String cpOperPdCl;
    private String cpOperDeCl;
    private String cpHvofBgnde;
    private String cpHvofEnddle;
    private String cpInduty;
    private String cpLctCl;
    private String cpThemaEnvrnCl;
    private String cpTourEraCl;
    private String cpFirstImageUrl;
    private String cpManageSttus;
    private String cpManageDivNm;
    private String cpMgcDiv;
    private String cpFacltDivNm;
    private String cpInsrncAt;
    private String cpTrsagntNo;
    private String cpBizrno;
    private String cpPrmisnDe;
    private String cpCreatedtime;
    private String cpModifiedtime;

    @OneToOne(mappedBy = "camp", cascade = CascadeType.ALL)
    private CampDetail campDetail;

}
