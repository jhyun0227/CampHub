package com.project.camphub.domain.camp.dto;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.common.dto.enumaration.YnType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CampDto {

    private String cpId;
    private String cpName;
    private String cpTel;
    private String cpHomepageUrl;
    private String cpResvUrl;
    private String cpThumbUrl;
    private String provinceCodeNm;
    private String districtCodeNm;
    private String cpZipcode;
    private String cpAddr;
    private String cpAddrDetail;
    private Double cpLon;
    private Double cpLat;
    private String cpDirections;
    private String cpTourBizNo;
    private String cpBizNo;
    private LocalDateTime cpPermitDt;
    private LocalDateTime cpCreateDt;
    private LocalDateTime cpModDt;
    private YnType cpIsActive;

    private String cpdIntro;
    private String cpdLineIntro;
    private String cpdFeatures;
    private String cpdTooltip;
    private String cpdOperDaysTypeValue;
    private String cpdMngStatTypeValue;
    private String cpdMngDivTypeValue;
    private String cpdMngOrg;
    private String cpdFcltDivTypeValue;
    private Integer cpdResStaffCnt;
    private LocalDateTime cpdOffStartDt;
    private LocalDateTime cpdOffEndDt;
    private YnType cpdCultEvYn;
    private String cpdCultEvNm;
    private YnType cpdExprPrgmYn;
    private String cpdExprPrgmNm;
    private String cpdAnimEntTypeValue;
    private YnType cpdPrvtCrvYn;
    private YnType cpdPrvtTrlYn;
    private YnType cpdInsuredYn;

    private Integer cpfTotalArea;
    private String cpfAmntyEtc;
    private String cpfNrbyFcltEtc;
    private Integer cpfGnrlSiteCnt;
    private Integer cpfCarSiteCnt;
    private Integer cpfGlampSiteCnt;
    private Integer cpfCrvSiteCnt;
    private Integer cpfPrvtCrvSiteCnt;
    private String cpfBrazierTypeValue;
    private Integer cpfSinkCnt;
    private Integer cpfToiletCnt;
    private Integer cpfSwrmCnt;
    private Integer cpfFireExtCnt;
    private Integer cpfFireWaterCnt;
    private Integer cpfFireSandCnt;
    private Integer cpfFireSensorCnt;

    private Integer cpsSiteDist;
    private Integer cpsSiteSize1Cnt;
    private Integer cpsSiteSize1Width;
    private Integer cpsSiteSize1Length;
    private Integer cpsSiteSize2Cnt;
    private Integer cpsSiteSize2Width;
    private Integer cpsSiteSize2Length;
    private Integer cpsSiteSize3Cnt;
    private Integer cpsSiteSize3Width;
    private Integer cpsSiteSize3Length;
    private Integer cpsBttmGrassCnt;
    private Integer cpsBttmStnCnt;
    private Integer cpsBttmTechCnt;
    private Integer cpsBttmGravelCnt;
    private Integer cpsBttmDirtCnt;

    private List<String> campAmenityNmList;
    private List<String> campCaravanInnerAmenityNmList;
    private List<String> campEquipmentRentalNmList;
    private List<String> campGlampingInnerAmenityNmList;
    private List<String> campIndustryNmList;
    private List<String> campLocationNmList;
    private List<String> campNearbyFacilityNmList;
    private List<String> campOperationSeasonNmList;
    private List<String> campReservationNmList;
    private List<String> campThemeNmList;
    private List<String> campTravelSeasonNmList;

    public static CampDto entityToDto(Camp camp) {
        return CampDto.builder()
                .cpId(camp.getCpId())
                .cpName(camp.getCpName())
                .cpTel(camp.getCpTel())
                .cpHomepageUrl(camp.getCpHomepageUrl())
                .cpResvUrl(camp.getCpResvUrl())
                .cpThumbUrl(camp.getCpThumbUrl())
                .provinceCodeNm(camp.getProvinceCode().getProvCdNm())
                .districtCodeNm(camp.getDistrictCode().getDistCdNm())
                .cpZipcode(camp.getCpZipcode())
                .cpAddr(camp.getCpAddr())
                .cpAddrDetail(camp.getCpAddrDetail())
                .cpLon(camp.getCpLon())
                .cpLat(camp.getCpLat())
                .cpDirections(camp.getCpDirections())
                .cpTourBizNo(camp.getCpTourBizNo())
                .cpBizNo(camp.getCpBizNo())
                .cpPermitDt(camp.getCpPermitDt())
                .cpCreateDt(camp.getCpCreateDt())
                .cpModDt(camp.getCpModDt())
                .cpIsActive(camp.getCpIsActive())

                .cpdIntro(camp.getCampDetail().getCpdIntro())
                .cpdLineIntro(camp.getCampDetail().getCpdLineIntro())
                .cpdFeatures(camp.getCampDetail().getCpdFeatures())
                .cpdTooltip(camp.getCampDetail().getCpdTooltip())
                .cpdOperDaysTypeValue(camp.getCampDetail().getCpdOperDaysType().getDescription())
                .cpdMngStatTypeValue(camp.getCampDetail().getCpdMngStatType().getDescription())
                .cpdMngDivTypeValue(camp.getCampDetail().getCpdMngDivType().getDescription())
                .cpdMngOrg(camp.getCampDetail().getCpdMngOrg())
                .cpdFcltDivTypeValue(camp.getCampDetail().getCpdFcltDivType().getDescription())
                .cpdResStaffCnt(camp.getCampDetail().getCpdResStaffCnt())
                .cpdOffStartDt(camp.getCampDetail().getCpdOffStartDt())
                .cpdOffEndDt(camp.getCampDetail().getCpdOffEndDt())
                .cpdCultEvYn(camp.getCampDetail().getCpdCultEvYn())
                .cpdCultEvNm(camp.getCampDetail().getCpdCultEvNm())
                .cpdExprPrgmYn(camp.getCampDetail().getCpdExprPrgmYn())
                .cpdExprPrgmNm(camp.getCampDetail().getCpdExprPrgmNm())
                .cpdAnimEntTypeValue(camp.getCampDetail().getCpdAnimEntType().getDescription())
                .cpdPrvtCrvYn(camp.getCampDetail().getCpdPrvtCrvYn())
                .cpdPrvtTrlYn(camp.getCampDetail().getCpdPrvtTrlYn())
                .cpdInsuredYn(camp.getCampDetail().getCpdInsuredYn())

                .cpfTotalArea(camp.getCampFacility().getCpfTotalArea())
                .cpfAmntyEtc(camp.getCampFacility().getCpfAmntyEtc())
                .cpfNrbyFcltEtc(camp.getCampFacility().getCpfNrbyFcltEtc())
                .cpfGnrlSiteCnt(camp.getCampFacility().getCpfGnrlSiteCnt())
                .cpfCarSiteCnt(camp.getCampFacility().getCpfCarSiteCnt())
                .cpfGlampSiteCnt(camp.getCampFacility().getCpfGlampSiteCnt())
                .cpfCrvSiteCnt(camp.getCampFacility().getCpfCrvSiteCnt())
                .cpfPrvtCrvSiteCnt(camp.getCampFacility().getCpfPrvtCrvSiteCnt())
                .cpfBrazierTypeValue(camp.getCampFacility().getCpfBrazierType().getDescription())
                .cpfSinkCnt(camp.getCampFacility().getCpfSinkCnt())
                .cpfToiletCnt(camp.getCampFacility().getCpfToiletCnt())
                .cpfSwrmCnt(camp.getCampFacility().getCpfSwrmCnt())
                .cpfFireExtCnt(camp.getCampFacility().getCpfFireExtCnt())
                .cpfFireWaterCnt(camp.getCampFacility().getCpfFireWaterCnt())
                .cpfFireSandCnt(camp.getCampFacility().getCpfFireSandCnt())
                .cpfFireSensorCnt(camp.getCampFacility().getCpfFireSensorCnt())

                .cpsSiteDist(camp.getCampSite().getCpsSiteDist())
                .cpsSiteSize1Cnt(camp.getCampSite().getCpsSiteSize1Cnt())
                .cpsSiteSize1Width(camp.getCampSite().getCpsSiteSize1Width())
                .cpsSiteSize1Length(camp.getCampSite().getCpsSiteSize1Length())
                .cpsSiteSize2Cnt(camp.getCampSite().getCpsSiteSize2Cnt())
                .cpsSiteSize2Width(camp.getCampSite().getCpsSiteSize2Width())
                .cpsSiteSize2Length(camp.getCampSite().getCpsSiteSize2Length())
                .cpsSiteSize3Cnt(camp.getCampSite().getCpsSiteSize3Cnt())
                .cpsSiteSize3Width(camp.getCampSite().getCpsSiteSize3Width())
                .cpsSiteSize3Length(camp.getCampSite().getCpsSiteSize3Length())
                .cpsBttmGrassCnt(camp.getCampSite().getCpsBttmGrassCnt())
                .cpsBttmStnCnt(camp.getCampSite().getCpsBttmStnCnt())
                .cpsBttmTechCnt(camp.getCampSite().getCpsBttmTechCnt())
                .cpsBttmGravelCnt(camp.getCampSite().getCpsBttmGravelCnt())
                .cpsBttmDirtCnt(camp.getCampSite().getCpsBttmDirtCnt())

                .build();
    }
}
