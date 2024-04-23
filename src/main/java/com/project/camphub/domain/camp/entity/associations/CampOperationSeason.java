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
public class CampOperationSeason implements Persistable<CampOperationSeason.CampOperationSeasonId>, CampAssociation {

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

    @Transient
    private boolean isNew = false;

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

    public static CampOperationSeason createCampOperationSeason(Camp camp, SeasonCode seasonCode) {
        CampOperationSeasonId id = new CampOperationSeasonId(camp.getCpId(), seasonCode.getSeasonCdId());
        return new CampOperationSeason(id, camp, seasonCode, true);
    }

    @Override
    public CampOperationSeasonId getId() {
        return getCampOperationSeasonId();
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
