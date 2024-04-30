package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.entity.associations.*;
import com.project.camphub.domain.common.entity.area.DistrictCode;
import com.project.camphub.domain.common.entity.area.ProvinceCode;
import com.project.camphub.common.dto.enumaration.YnType;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.project.camphub.common.utils.CoordinateUtils.parseLonOrLatToDouble;
import static com.project.camphub.common.utils.DateUtils.parseStringToLocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = {"campDetail", "campFacility", "campSite", "campAmenityList", "campCaravanInnerAmenityList", "campEquipmentRentalList",
        "campGlampingInnerAmenityList", "campIndustryList", "campLocationList", "campNearbyFacilityList", "campOperationSeasonList",
        "campOperationSeasonList", "campThemeList", "campTravelSeasonList"})
public class Camp implements Persistable<String> {

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

    private Integer cpReadCount;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType cpIsActive;

    //캠프 상세, 시설, 사이트
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private CampDetail campDetail;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private CampFacility campFacility;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private CampSite campSite;

    public void linkToCampDetail(CampDetail campDetail) { this.campDetail = campDetail; }
    public void linkToCampFacility(CampFacility campFacility) { this.campFacility = campFacility; }
    public void linkToCampSite(CampSite campSite) { this.campSite = campSite; }

    //캠프코드 연관관계
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampAmenity> campAmenityList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampCaravanInnerAmenity> campCaravanInnerAmenityList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampEquipmentRental> campEquipmentRentalList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampGlampingInnerAmenity> campGlampingInnerAmenityList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampIndustry> campIndustryList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampLocation> campLocationList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampNearbyFacility> campNearbyFacilityList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampOperationSeason> campOperationSeasonList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampReservation> campReservationList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampTheme> campThemeList = new ArrayList<>();
//    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "camp")
    private List<CampTravelSeason> campTravelSeasonList = new ArrayList<>();

    @Transient
    private boolean isNew = false;

    @Override
    public String getId() {
        return this.getCpId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public static Camp apiToEntity(OpenApiResponse.Item item, Map<String, ProvinceCode> provinceCodeMap, Map<String, DistrictCode> districtCodeMap) {
        return Camp.builder()
                .cpId(item.getContentId())
                .cpName(item.getFacltNm())
                .cpTel(item.getTel())
                .cpHomepageUrl(item.getHomepage())
                .cpResvUrl(item.getResveUrl())
                .cpThumbUrl(item.getFirstImageUrl())
                .provinceCode(setProvinceCode(item, provinceCodeMap))
                .districtCode(setDistrictCode(item, districtCodeMap))
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
                .cpReadCount(0)
                .cpIsActive(checkIsActive(item.getSyncStatus()))
                .isNew(true)
                .build();
    }

    private static ProvinceCode setProvinceCode(OpenApiResponse.Item item, Map<String, ProvinceCode> provinceCodeMap) {
        return provinceCodeMap.get(item.getDoNm());
    }

    private static DistrictCode setDistrictCode(OpenApiResponse.Item item, Map<String, DistrictCode> districtCodeMap) {
        String key = item.getDoNm() + ":" + item.getSigunguNm();
        return districtCodeMap.get(key);
    }

    private static YnType checkIsActive(String syncStatus) {
        if ("D".equals(syncStatus)) {
            return YnType.N;
        }

        return YnType.Y;
    }

    public void updateCamp(OpenApiResponse.Item item, Map<String, ProvinceCode> provinceCodeMap, Map<String, DistrictCode> districtCodeMap) {
        this.cpName = item.getFacltNm();
        this.cpTel = item.getTel();
        this.cpHomepageUrl = item.getHomepage();
        this.cpResvUrl = item.getResveUrl();
        this.cpThumbUrl = item.getFirstImageUrl();
        this.provinceCode = setProvinceCode(item, provinceCodeMap);
        this.districtCode = setDistrictCode(item, districtCodeMap);
        this.cpZipcode = item.getZipcode();
        this.cpAddr = item.getAddr1();
        this.cpAddrDetail = item.getAddr2();
        this.cpLon = parseLonOrLatToDouble(item.getMapX());
        this.cpLat = parseLonOrLatToDouble(item.getMapY());
        this.cpDirections = item.getDirection();
        this.cpTourBizNo = item.getTrsagntNo();
        this.cpBizNo = item.getBizrno();
        this.cpPermitDt = parseStringToLocalDateTime(item.getPrmisnDe());
        this.cpCreateDt = parseStringToLocalDateTime(item.getCreatedtime());
        this.cpModDt = parseStringToLocalDateTime(item.getModifiedtime());
        this.cpIsActive = checkIsActive(item.getSyncStatus());
    }

    public List<Long> getCampAmenityIdList() {
        return campAmenityList.stream()
                .map(campAmenity -> campAmenity.getAmenityCode().getAmntyCdId())
                .toList();
    }
    public List<Long> getCampCaravanInnerAmenityIdList() {
        return campCaravanInnerAmenityList.stream()
                .map(campCaravanInnerAmenity -> campCaravanInnerAmenity.getCampCaravanInnerAmenityId().getInnerAmntyCdId())
                .toList();
    }
    public List<Long> getCampEquipmentRentalIdList() {
        return campEquipmentRentalList.stream()
                .map(campEquipmentRental -> campEquipmentRental.getCampEquipmentRentalId().getEquipCdId())
                .toList();
    }
    public List<Long> getCampGlampingInnerAmenityIdList() {
        return campGlampingInnerAmenityList.stream()
                .map(campGlampingInnerAmenity -> campGlampingInnerAmenity.getCampGlampingInnerAmenityId().getInnerAmntyCdId())
                .toList();
    }
    public List<Long> getCampIndustryIdList() {
        return campIndustryList.stream()
                .map(campIndustry -> campIndustry.getCampIndustryId().getIndstCdId())
                .toList();
    }
    public List<Long> getCampLocationIdList() {
        return campLocationList.stream()
                .map(campLocation -> campLocation.getCampLocationId().getLoctCdId())
                .toList();
    }
    public List<Long> getCampNearbyFacilityIdList() {
        return campNearbyFacilityList.stream()
                .map(campNearbyFacility -> campNearbyFacility.getCampNearbyFacilityId().getNrbyFcltCdId())
                .toList();
    }
    public List<Long> getCampOperationSeasonIdList() {
        return campOperationSeasonList.stream()
                .map(campOperationSeason -> campOperationSeason.getCampOperationSeasonId().getSeasonCdId())
                .toList();
    }
    public List<Long> getCampReservationIdList() {
        return campReservationList.stream()
                .map(campReservation -> campReservation.getCampReservationId().getResvCdId())
                .toList();
    }
    public List<Long> getCampThemeIdList() {
        return campThemeList.stream()
                .map(campTheme -> campTheme.getCampThemeId().getThemeCdId())
                .toList();
    }
    public List<Long> getCampTravelSeasonIdList() {
        return campTravelSeasonList.stream()
                .map(campTravelSeason -> campTravelSeason.getCampTravelSeasonId().getSeasonCdId())
                .toList();
    }

    public void addReadCount() {
        this.cpReadCount++;
    }
}
