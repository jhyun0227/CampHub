package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampAmenityId;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampAmenity {

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

    public CampAmenity(Camp camp, AmenityCode amenityCode) {
        this.camp = camp;
        this.amenityCode = amenityCode;
    }
}
