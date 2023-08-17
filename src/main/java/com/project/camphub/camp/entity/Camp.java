package com.project.camphub.camp.entity;

import com.project.camphub.externalapi.dto.openapi.Item;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

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
    @Column(length = 2000)
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
    private String cpSyncStatus;

    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CampDetail campDetail;

    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CampFacility campFacility;

    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CampSite campSite;

    /**
     * 데이터 저장
     * OpenApiResponse -> Camp
     */
    public static Camp fromOpenApiResponse(Item item) {
        return Camp.builder()
                .cpId(UUID.randomUUID().toString())
                .cpContentId(item.getContentId())
                .cpFacltNm(item.getFacltNm())
                .cpTel(item.getTel())
                .cpHomepage(item.getHomepage())
                .cpResveCl(item.getResveCl())
                .cpResveUrl(item.getResveUrl())
                .cpOperPdCl(item.getOperPdCl())
                .cpOperDeCl(item.getOperDeCl())
                .cpHvofBgnde(item.getHvofBgnde())
                .cpHvofEnddle(item.getHvofEnddle())
                .cpInduty(item.getInduty())
                .cpLctCl(item.getLctCl())
                .cpThemaEnvrnCl(item.getThemaEnvrnCl())
                .cpTourEraCl(item.getTourEraCl())
                .cpFirstImageUrl(item.getFirstImageUrl())
                .cpManageSttus(item.getManageSttus())
                .cpManageDivNm(item.getMangeDivNm())
                .cpMgcDiv(item.getMgcDiv())
                .cpFacltDivNm(item.getFacltDivNm())
                .cpInsrncAt(item.getInsrncAt())
                .cpTrsagntNo(item.getTrsagntNo())
                .cpBizrno(item.getBizrno())
                .cpPrmisnDe(item.getPrmisnDe())
                .cpCreatedtime(item.getCreatedtime())
                .cpModifiedtime(item.getModifiedtime())
                .cpSyncStatus(item.getSyncStatus())
                .build();
    }

    /**
     * 데이터 수정
     * OpenApiResponse -> Camp
     */
    public void fromSyncOpenApiResponse(Item item) {
        this.cpFacltNm = item.getFacltNm();
        this.cpTel = item.getTel();
        this.cpHomepage = item.getHomepage();
        this.cpResveCl = item.getResveCl();
        this.cpResveUrl = item.getResveUrl();
        this.cpOperPdCl = item.getOperPdCl();
        this.cpOperDeCl = item.getOperDeCl();
        this.cpHvofBgnde = item.getHvofBgnde();
        this.cpHvofEnddle = item.getHvofEnddle();
        this.cpInduty = item.getInduty();
        this.cpLctCl = item.getLctCl();
        this.cpThemaEnvrnCl = item.getThemaEnvrnCl();
        this.cpTourEraCl = item.getTourEraCl();
        this.cpFirstImageUrl = item.getFirstImageUrl();
        this.cpManageSttus = item.getManageSttus();
        this.cpManageDivNm = item.getMangeDivNm();
        this.cpMgcDiv = item.getMgcDiv();
        this.cpFacltDivNm = item.getFacltDivNm();
        this.cpInsrncAt = item.getInsrncAt();
        this.cpTrsagntNo = item.getTrsagntNo();
        this.cpBizrno = item.getBizrno();
        this.cpPrmisnDe = item.getPrmisnDe();
        this.cpCreatedtime = item.getCreatedtime();
        this.cpModifiedtime = item.getModifiedtime();
        this.cpSyncStatus = item.getSyncStatus();
    }

    /**
     * Camp에 CampDetail, CampFacility, CampSite를 참조
     */
    public void refCpdCpfCps(CampDetail campDetail, CampFacility campFacility, CampSite campSite) {
        this.campDetail = campDetail;
        this.campFacility = campFacility;
        this.campSite = campSite;
    }

}
