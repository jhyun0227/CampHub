package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.SeasonCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampTravelSeason implements Persistable<CampTravelSeason.CampTravelSeasonId>, CampAssociation {

    @EmbeddedId
    private CampTravelSeasonId campTravelSeasonId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "seasonCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_cd_id")
    private SeasonCode seasonCode;

    @Transient
    private boolean isNew = false;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampTravelSeasonId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long seasonCdId;
    }

    public static CampTravelSeason createCampTravelSeason(Camp camp, SeasonCode seasonCode) {
        CampTravelSeasonId id = new CampTravelSeasonId(camp.getCpId(), seasonCode.getSeasonCdId());
        return new CampTravelSeason(id, camp, seasonCode, true);
    }

    @Override
    public CampTravelSeasonId getId() {
        return getCampTravelSeasonId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public String getCampCodeNm() {
        return seasonCode.getSeasonCdNm();
    }
}
