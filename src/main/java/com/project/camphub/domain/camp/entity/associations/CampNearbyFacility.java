package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampNearbyFacilityId;
import com.project.camphub.domain.camp.entity.code.NearbyFacilityCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampNearbyFacility {

    @EmbeddedId
    private CampNearbyFacilityId campNearbyFacilityId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "nrbyFcltCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nrby_fclt_cd_id")
    private NearbyFacilityCode nearbyFacilityCode;

    public CampNearbyFacility(Camp camp, NearbyFacilityCode nearbyFacilityCode) {
        this.camp = camp;
        this.nearbyFacilityCode = nearbyFacilityCode;
    }
}
