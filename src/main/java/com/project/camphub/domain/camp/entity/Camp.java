package com.project.camphub.domain.camp.entity;

import com.project.camphub.common.holder.AreaMappingHolder;
import com.project.camphub.common.utils.CoordinateUtils;
import com.project.camphub.common.utils.DateUtils;
import com.project.camphub.domain.camp.entity.associations.*;
import com.project.camphub.domain.common.entity.area.DistrictCode;
import com.project.camphub.domain.common.entity.area.ProvinceCode;
import com.project.camphub.domain.common.enumaration.YnType;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.camphub.common.utils.CoordinateUtils.*;
import static com.project.camphub.common.utils.DateUtils.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Camp {

    @Id
    @Column(length = 10)
    private String cpId;
    @Column(length = 100)
    private String cpName;
    @Column(length = 30)
    private String cpTel;
    @Column(length = 2000)
    private String cpHomepageUrl;
    @Column(length = 2000)
    private String cpResvUrl;
    @Column(length = 500)
    private String cpThumbUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_prov_cd_id")
    private ProvinceCode provinceCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_dist_cd_id")
    private DistrictCode districtCode;

    @Column(length = 20)
    private String cpZipcode;
    @Column(length = 100)
    private String cpAddr;
    @Column(length = 100)
    private String cpAddrDetail;

    private Double cpLon;
    private Double cpLat;

    @Column(length = 1000)
    private String cpDirections;
    @Column(length = 50)
    private String cpTourBizNo;
    @Column(length = 50)
    private String cpBizNo;

    private LocalDateTime cpPermitDt;
    private LocalDateTime cpCreateDt;
    private LocalDateTime cpModDt;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType cpIsActive;

    //캠프 상세, 시설, 사이트
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampDetail campDetail;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampFacility campFacility;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampSite campSite;

    public static Camp apiToEntity(OpenApiResponse.Item item, AreaMappingHolder areaMappingHolder) {
        return Camp.builder()
                .cpId(item.getContentId())
                .cpName(item.getFacltNm())
                .cpTel(item.getTel())
                .cpHomepageUrl(item.getHomepage())
                .cpResvUrl(item.getResveUrl())
                .cpThumbUrl(item.getFirstImageUrl())
                .provinceCode(areaMappingHolder.getNameToProvCdMap().get(item.getDoNm()))
                .districtCode(areaMappingHolder.getNameToDistCdMap().get(item.getSigunguNm()))
                .cpZipcode(item.getZipcode())
                .cpAddr(item.getAddr1())
                .cpAddrDetail(item.getAddr2())
                .cpLon(parseLonOrLatToDouble(item.getMapX()))
                .cpLat(parseLonOrLatToDouble(item.getMapY()))
                .cpDirections(item.getDirection())
                .cpTourBizNo(item.getTrsagntNo())
                .cpBizNo(item.getBizrno())
                .cpPermitDt(parseStringToLocalDateTime(item.getPrmisnDe()))
                .cpCreateDt(parseStringToLocalDateTime(item.getCreatedtime()))
                .cpModDt(parseStringToLocalDateTime(item.getModifiedtime()))
                .cpIsActive(checkIsActive(item.getSyncStatus()))
                .build();
    }

    private static YnType checkIsActive(String syncStatus) {
        if ("D".equals(syncStatus)) {
            return YnType.N;
        }

        return YnType.Y;
    }


}
