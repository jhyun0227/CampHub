package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampGlampingInnerAmenity {

    @EmbeddedId
    private CampGlampingInnerAmenityId campGlampingInnerAmenityId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "innerAmntyCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inner_amnty_cd_id")
    private InnerAmenityCode innerAmenityCode;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampGlampingInnerAmenityId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long innerAmntyCdId;
    }

    public static CampGlampingInnerAmenity createCampGlampingInnerAmenity(Camp camp, InnerAmenityCode innerAmenityCode) {
        CampGlampingInnerAmenityId id = new CampGlampingInnerAmenityId(camp.getCpId(), innerAmenityCode.getInnerAmntyCdId());
        return new CampGlampingInnerAmenity(id, camp, innerAmenityCode);
    }
}
