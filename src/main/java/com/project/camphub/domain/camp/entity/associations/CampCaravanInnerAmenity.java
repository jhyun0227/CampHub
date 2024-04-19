package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampCaravanInnerAmenity implements Persistable<CampCaravanInnerAmenity.CampCaravanInnerAmenityId> {

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

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampCaravanInnerAmenityId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long innerAmntyCdId;
    }

    public static void createCampCaravanInnerAmenityAndLinkToCamp(Camp camp, InnerAmenityCode innerAmenityCode) {
        CampCaravanInnerAmenityId id = new CampCaravanInnerAmenityId(camp.getCpId(), innerAmenityCode.getInnerAmntyCdId());
        CampCaravanInnerAmenity campCaravanInnerAmenity = new CampCaravanInnerAmenity(id, camp, innerAmenityCode);

        camp.getCampCaravanInnerAmenityList().add(campCaravanInnerAmenity);
    }

    @Override
    public CampCaravanInnerAmenityId getId() {
        return getCampCaravanInnerAmenityId();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
