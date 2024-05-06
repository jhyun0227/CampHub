package com.project.camphub.domain.camp.dto;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "캠핑 목록 DTO")
public class CampListDto {

    @Schema(description = "캠프ID")
    private String cpId;
    @Schema(description = "캠핑장명")
    private String cpName;
    @Schema(description = "전화번호")
    private String cpTel;
    @Schema(description = "시도명")
    private String provinceName;
    @Schema(description = "시군구명")
    private String districtName;
    @Schema(description = "주소")
    private String cpAddr;
    @Schema(description = "대표이미지 URL")
    private String cpThumbUrl;
    @Schema(description = "소개")
    private String cpdIntro;
    @Schema(description = "한줄 소개")
    private String cpdLineIntro;
    @Schema(description = "조회수")
    private Integer cpReadCount;
    @Schema(description = "편의시설 목록")
    private List<CampCodeDto> campAmenityList;

    public static CampListDto entityToDto(Camp camp) {

        List<CampCodeDto> campCodeDtoList = new ArrayList<>();

        if (!camp.getCampAmenityList().isEmpty()) {
            camp.getCampAmenityList().forEach(campAmenity -> {
                AmenityCode amenityCode = campAmenity.getAmenityCode();
                campCodeDtoList.add(new CampCodeDto(amenityCode.getAmntyCdId(), amenityCode.getAmntyCdNm()));
            });
        }

        return CampListDto.builder()
                .cpId(camp.getCpId())
                .cpName(camp.getCpName())
                .cpTel(camp.getCpTel())
                .provinceName(camp.getProvinceCode().getProvCdNm())
                .districtName(camp.getDistrictCode().getDistCdNm())
                .cpAddr(camp.getCpAddr())
                .cpThumbUrl(camp.getCpThumbUrl())
                .cpdIntro(camp.getCampDetail().getCpdIntro())
                .cpdLineIntro(camp.getCampDetail().getCpdLineIntro())
                .cpReadCount(camp.getCpReadCount())
                .campAmenityList(campCodeDtoList)
                .build();
    }
}
