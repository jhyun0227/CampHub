package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampEquipmentRentalId;
import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
