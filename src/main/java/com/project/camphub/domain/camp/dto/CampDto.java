package com.project.camphub.domain.camp.dto;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.common.dto.enumaration.YnType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "캠핑 DTO")
public class CampDto {

    @Schema(description = "캠프ID")
    private String cpId;
    @Schema(description = "캠핑장명")
    private String cpName;
    @Schema(description = "전화번호")
    private String cpTel;
    @Schema(description = "홈페이지 URL")
    private String cpHomepageUrl;
    @Schema(description = "예약페이지 URL")
    private String cpResvUrl;
    @Schema(description = "대표이미지 URL")
    private String cpThumbUrl;
    @Schema(description = "시도명")
    private String provinceCodeNm;
    @Schema(description = "시군구명")
    private String districtCodeNm;
    @Schema(description = "우편번호")
    private String cpZipcode;
    @Schema(description = "주소")
    private String cpAddr;
    @Schema(description = "주소 상세")
    private String cpAddrDetail;
    @Schema(description = "경도")
    private Double cpLon;
    @Schema(description = "위도")
    private Double cpLat;
    @Schema(description = "오는 방법")
    private String cpDirections;
    @Schema(description = "관광사업자번호")
    private String cpTourBizNo;
    @Schema(description = "사업자번호")
    private String cpBizNo;
    @Schema(description = "인허가일자")
    private LocalDateTime cpPermitDt;
    @Schema(description = "등록일")
    private LocalDateTime cpCreateDt;
    @Schema(description = "수정일")
    private LocalDateTime cpModDt;
    @Schema(description = "조회수")
    private Integer cpReadCount;
    @Schema(description = "콘텐츠 상태")
    private YnType cpIsActive;

    @Schema(description = "소개")
    private String cpdIntro;
    @Schema(description = "한줄 소개")
    private String cpdLineIntro;
    @Schema(description = "특징")
    private String cpdFeatures;
    @Schema(description = "툴팁")
    private String cpdTooltip;
    @Schema(description = "운영일")
    private String cpdOperDaysTypeValue;
    @Schema(description = "운영상태, 관리상태")
    private String cpdMngStatTypeValue;
    @Schema(description = "운영주체, 관리주체")
    private String cpdMngDivTypeValue;
    @Schema(description = "운영기관, 관리기관")
    private String cpdMngOrg;
    @Schema(description = "사업주체, 구분")
    private String cpdFcltDivTypeValue;
    @Schema(description = "상주관리인원")
    private Integer cpdResStaffCnt;
    @Schema(description = "휴장기간 시작일")
    private LocalDateTime cpdOffStartDt;
    @Schema(description = "휴장기간 종료일")
    private LocalDateTime cpdOffEndDt;
    @Schema(description = "자체문화행사 여부")
    private YnType cpdCultEvYn;
    @Schema(description = "자체문화행사명")
    private String cpdCultEvNm;
    @Schema(description = "체험프로그램 여부")
    private YnType cpdExprPrgmYn;
    @Schema(description = "체험프로그램명")
    private String cpdExprPrgmNm;
    @Schema(description = "애완동물출입")
    private String cpdAnimEntTypeValue;
    @Schema(description = "개인 카라반 동반 여부")
    private YnType cpdPrvtCrvYn;
    @Schema(description = "개인 트레일러 동반 여부")
    private YnType cpdPrvtTrlYn;
    @Schema(description = "영업배상책임보험 가입 여부")
    private YnType cpdInsuredYn;

    @Schema(description = "전체 면적")
    private Integer cpfTotalArea;
    @Schema(description = "부대시설 기타")
    private String cpfAmntyEtc;
    @Schema(description = "주변이용가능시설 기타")
    private String cpfNrbyFcltEtc;
    @Schema(description = "일반아영장 사이트 개수")
    private Integer cpfGnrlSiteCnt;
    @Schema(description = "자동차야영장 사이트 개수")
    private Integer cpfCarSiteCnt;
    @Schema(description = "글램핑 사이트 개수")
    private Integer cpfGlampSiteCnt;
    @Schema(description = "카라반 사이트 개수")
    private Integer cpfCrvSiteCnt;
    @Schema(description = "개인 카라반 사이트 개수")
    private Integer cpfPrvtCrvSiteCnt;
    @Schema(description = "화로대")
    private String cpfBrazierTypeValue;
    @Schema(description = "개수대 개수")
    private Integer cpfSinkCnt;
    @Schema(description = "화장실 개수")
    private Integer cpfToiletCnt;
    @Schema(description = "샤워실 개수")
    private Integer cpfSwrmCnt;
    @Schema(description = "소화기 개수")
    private Integer cpfFireExtCnt;
    @Schema(description = "방화수 개수")
    private Integer cpfFireWaterCnt;
    @Schema(description = "방화사 개수")
    private Integer cpfFireSandCnt;
    @Schema(description = "화재감시기 개수")
    private Integer cpfFireSensorCnt;

    @Schema(description = "사이트간 거리")
    private Integer cpsSiteDist;
    @Schema(description = "사이트 크기1 개수")
    private Integer cpsSiteSize1Cnt;
    @Schema(description = "사이트 크기1 가로")
    private Integer cpsSiteSize1Width;
    @Schema(description = "사이트 크기1 세로")
    private Integer cpsSiteSize1Length;
    @Schema(description = "사이트 크기2 개수")
    private Integer cpsSiteSize2Cnt;
    @Schema(description = "사이트 크기2 가로")
    private Integer cpsSiteSize2Width;
    @Schema(description = "사이트 크기2 세로")
    private Integer cpsSiteSize2Length;
    @Schema(description = "사이트 크기3 개수")
    private Integer cpsSiteSize3Cnt;
    @Schema(description = "사이트 크기3 가로")
    private Integer cpsSiteSize3Width;
    @Schema(description = "사이트 크기3 세로")
    private Integer cpsSiteSize3Length;
    @Schema(description = "사이트 바닥 - 잔디")
    private Integer cpsBttmGrassCnt;
    @Schema(description = "사이트 바닥 - 파쇄석")
    private Integer cpsBttmStnCnt;
    @Schema(description = "사이트 바닥 - 테크")
    private Integer cpsBttmTechCnt;
    @Schema(description = "사이트 바닥 - 자갈")
    private Integer cpsBttmGravelCnt;
    @Schema(description = "사이트 바닥 - 맨흙")
    private Integer cpsBttmDirtCnt;

    @Schema(description = "편의시설 목록")
    private List<String> campAmenityNmList;
    @Schema(description = "카라반 내부 편의시설 목록")
    private List<String> campCaravanInnerAmenityNmList;
    @Schema(description = "캠핑장비 대여 가능 목록")
    private List<String> campEquipmentRentalNmList;
    @Schema(description = "글램핑 내부 편의시설 목록")
    private List<String> campGlampingInnerAmenityNmList;
    @Schema(description = "업종 목록")
    private List<String> campIndustryNmList;
    @Schema(description = "입지 목록")
    private List<String> campLocationNmList;
    @Schema(description = "근처 편의시설 목록")
    private List<String> campNearbyFacilityNmList;
    @Schema(description = "운영시기 목록")
    private List<String> campOperationSeasonNmList;
    @Schema(description = "예약구분 목록")
    private List<String> campReservationNmList;
    @Schema(description = "테마 목록")
    private List<String> campThemeNmList;
    @Schema(description = "여행시기 목록")
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
                .cpReadCount(camp.getCpReadCount())
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
