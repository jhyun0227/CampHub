package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.common.registry.AreaMapRegistry;
import com.project.camphub.domain.common.entity.area.DistrictCode;
import com.project.camphub.domain.common.entity.area.ProvinceCode;
import com.project.camphub.domain.common.enumaration.YnType;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.Map;

import static com.project.camphub.common.utils.CoordinateUtils.*;
import static com.project.camphub.common.utils.DateUtils.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@ToString
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

    /**
     * persistable 구현메서드
     * isNew()를 무조건 true를 반환하는 이유
     * repository.save()를 호출하면 Entity의 PK 유무로 persist(), merge() 분기가 나뉜다.
     * Id를 수동으로 할당하는 방식을 사용하기 때문에 merge()를 호출하게 되는데 select 쿼리를 한번 DB에 전달하고
     * 없을 경우 insert 쿼리를 다시 전달하기 때문에 성능상으로 비효율적이게 된다.
     * Camp 관련 Entity들이 외부 API를 통해 데이터를 적재하는 것 외에 repository.save()를 호출하는 경우는 없을 것으로 판단된다.
     * 수정의 경우에는 더티 체킹을 사용하는것을 명심하도록 하자.
     */
    @Override
    public String getId() {
        return this.getCpId();
    }

    @Override
    public boolean isNew() {
        return true;
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
                .cpIsActive(checkIsActive(item.getSyncStatus()))
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
        this.cpName = item.getFeatureNm();
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
}
