package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampIndustryId;
import com.project.camphub.domain.camp.entity.code.IndustryCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
