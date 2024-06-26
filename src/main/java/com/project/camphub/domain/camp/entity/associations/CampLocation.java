package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.LocationCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampLocation implements Persistable<CampLocation.CampLocationId>, CampAssociation {

    @EmbeddedId
    private CampLocationId campLocationId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "loctCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loct_cd_id")
    private LocationCode locationCode;

    @Transient
    private boolean isNew = false;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampLocationId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long loctCdId;
    }

    public static CampLocation createCampLocation(Camp camp, LocationCode locationCode) {
        CampLocationId id = new CampLocationId(camp.getCpId(), locationCode.getLoctCdId());
        return new CampLocation(id, camp, locationCode, true);
    }

    @Override
    public CampLocationId getId() {
        return getCampLocationId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public String getCampCodeNm() {
        return locationCode.getLoctCdNm();
    }
}
