package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampEquipmentRental {

    @EmbeddedId
    private CampEquipmentRentalId campEquipmentRentalId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "equipCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equip_cd_id")
    private EquipmentCode equipmentCode;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampEquipmentRentalId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long equipCdId;
    }

    public static CampEquipmentRental createCampEquipmentRental(Camp camp, EquipmentCode equipmentCode) {
        CampEquipmentRentalId id = new CampEquipmentRentalId(camp.getCpId(), equipmentCode.getEquipCdId());
        return new CampEquipmentRental(id, camp, equipmentCode);
    }
}
