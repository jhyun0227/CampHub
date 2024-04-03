package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.entity.associations.*;
import com.project.camphub.domain.common.entity.area.DistrictCode;
import com.project.camphub.domain.common.entity.area.ProvinceCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 1)
    private String cpIsActive;

    //캠프 상세, 시설, 사이트
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampDetail campDetail;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampFacility campFacility;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampSite campSite;

    //캠프, 코드 연관관계
    @OneToMany(mappedBy = "camp")
    private List<CampAmenity> campAmenityList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampEquipmentRental> campEquipmentRentalList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampIndustry> campIndustryList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampCaravanInnerAmenity> campCaravanInnerAmenityList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampGlampingInnerAmenity> campGlampingInnerAmenityList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampLocation> campLocationList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampNearbyFacility> campNearbyFacilityList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampReservation> campReservationList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampOperationSeason> campOperationSeasonList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampTravelSeason> campTravelSeasonList = new ArrayList<>();
    @OneToMany(mappedBy = "camp")
    private List<CampTheme> campThemeList = new ArrayList<>();

}
