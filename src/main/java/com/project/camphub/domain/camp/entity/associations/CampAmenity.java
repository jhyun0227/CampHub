package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampAmenity implements Persistable<CampAmenity.CampAmenityId> {

    @EmbeddedId
    private CampAmenityId campAmenityId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "amntyCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amnty_cd_id")
    private AmenityCode amenityCode;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampAmenityId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long amntyCdId;
    }

    public static CampAmenity createCampAmenity(Camp camp, AmenityCode amenityCode) {
        CampAmenityId id = new CampAmenityId(camp.getCpId(), amenityCode.getAmntyCdId());
        return new CampAmenity(id, camp, amenityCode);
    }

    @Override
    public CampAmenityId getId() {
        return getCampAmenityId();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
