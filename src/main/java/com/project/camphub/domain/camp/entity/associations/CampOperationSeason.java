package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampOperationSeasonId;
import com.project.camphub.domain.camp.entity.code.SeasonCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampOperationSeason {

    @EmbeddedId
    private CampOperationSeasonId campOperationSeasonId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "seasonCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_cd_id")
    private SeasonCode seasonCode;

    public CampOperationSeason(Camp camp, SeasonCode seasonCode) {
        this.camp = camp;
        this.seasonCode = seasonCode;
    }
}
