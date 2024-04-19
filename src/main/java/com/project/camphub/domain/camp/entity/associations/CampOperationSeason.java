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
public class CampOperationSeason implements Persistable<CampOperationSeason.CampOperationSeasonId> {

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

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampOperationSeasonId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long seasonCdId;
    }

    public static void createCampOperationSeasonAndLinkToCamp(Camp camp, SeasonCode seasonCode) {
        CampOperationSeasonId id = new CampOperationSeasonId(camp.getCpId(), seasonCode.getSeasonCdId());
        CampOperationSeason campOperationSeason = new CampOperationSeason(id, camp, seasonCode);

        camp.getCampOperationSeasonList().add(campOperationSeason);
    }

    @Override
    public CampOperationSeasonId getId() {
        return getCampOperationSeasonId();
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
