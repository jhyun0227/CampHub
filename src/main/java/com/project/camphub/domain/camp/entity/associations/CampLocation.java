package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampLocationId;
import com.project.camphub.domain.camp.entity.code.LocationCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampLocation {

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
}
