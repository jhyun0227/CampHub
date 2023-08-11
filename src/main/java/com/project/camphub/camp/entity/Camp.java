package com.project.camphub.camp.entity;

import com.project.camphub.externalapi.dto.openapi.Item;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    /**
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
                .build();
    }
}
