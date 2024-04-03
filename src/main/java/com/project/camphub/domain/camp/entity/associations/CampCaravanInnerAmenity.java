package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampCaravanInnerAmenityId;
import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampCaravanInnerAmenity {

    @EmbeddedId
    private CampCaravanInnerAmenityId campCaravanInnerAmenityId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "innerAmntyCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inner_amnty_cd_id")
    private InnerAmenityCode innerAmenityCode;
}