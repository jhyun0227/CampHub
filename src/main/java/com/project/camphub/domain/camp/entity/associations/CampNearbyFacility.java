package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.NearbyFacilityCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampNearbyFacility implements Persistable<CampNearbyFacility.CampNearbyFacilityId> {

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

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampNearbyFacilityId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long nrbyFcltCdId;
    }

    public static CampNearbyFacility createCampNearbyFacility(Camp camp, NearbyFacilityCode nearbyFacilityCode) {
        CampNearbyFacilityId id = new CampNearbyFacilityId(camp.getCpId(), nearbyFacilityCode.getNrbyFcltCdId());
        return new CampNearbyFacility(id, camp, nearbyFacilityCode);
    }

    @Override
    public CampNearbyFacilityId getId() {
        return getCampNearbyFacilityId();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
