package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.IndustryCode;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampIndustry {

    @EmbeddedId
    private CampIndustryId campIndustryId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId("indstCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indst_cd_id")
    private IndustryCode industryCode;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampIndustryId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long indstCdId;
    }

    public static CampIndustry createCampIndustry(Camp camp, IndustryCode industryCode) {
        CampIndustryId id = new CampIndustryId(camp.getCpId(), industryCode.getIndstCdId());
        return new CampIndustry(id, camp, industryCode);
    }
}
