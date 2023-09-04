package com.project.camphub.camp.dto;

import com.project.camphub.camp.entity.Camp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.StringTokenizer;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class CampDto {

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
    private String cpSyncStatus;


    private CampDetailDto campDetail;
    private CampFacilityDto campFacility;
    private CampSiteDto campSite;

    /**
     * Entity -> Dto
     */
    public static CampDto fromEntity(Camp camp) {
        return CampDto.builder()
                .cpId(camp.getCpId())
                .cpContentId(camp.getCpContentId())
                .cpFacltNm(camp.getCpFacltNm())
                .cpTel(camp.getCpTel())
                .cpHomepage(camp.getCpHomepage())
                .cpResveCl(camp.getCpResveCl())
                .cpResveUrl(camp.getCpResveUrl())
                .cpOperPdCl(camp.getCpOperPdCl())
                .cpOperDeCl(camp.getCpOperDeCl())
                .cpHvofBgnde(camp.getCpHvofBgnde())
                .cpHvofEnddle(camp.getCpHvofEnddle())
                .cpInduty(camp.getCpInduty())
                .cpLctCl(camp.getCpLctCl())
                .cpThemaEnvrnCl(camp.getCpThemaEnvrnCl())
                .cpTourEraCl(camp.getCpTourEraCl())
                .cpFirstImageUrl(camp.getCpFirstImageUrl())
                .cpManageSttus(camp.getCpManageSttus())
                .cpManageDivNm(camp.getCpManageDivNm())
                .cpMgcDiv(camp.getCpMgcDiv())
                .cpFacltDivNm(camp.getCpFacltDivNm())
                .cpInsrncAt(camp.getCpInsrncAt())
                .cpTrsagntNo(camp.getCpTrsagntNo())
                .cpBizrno(camp.getCpBizrno())
                .cpPrmisnDe(camp.getCpPrmisnDe())
                .cpCreatedtime(camp.getCpCreatedtime())
                .cpModifiedtime(camp.getCpModifiedtime())
                .cpSyncStatus(camp.getCpSyncStatus())
                .campDetail(CampDetailDto.fromEntity(camp.getCampDetail()))
                .campFacility(CampFacilityDto.fromEntity(camp.getCampFacility()))
                .campSite(CampSiteDto.fromEntity(camp.getCampSite()))
                .build();
    }
}
