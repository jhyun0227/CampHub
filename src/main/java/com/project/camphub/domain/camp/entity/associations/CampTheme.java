package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.ThemeCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampTheme implements Persistable<CampTheme.CampThemeId>, CampAssociation {

    @EmbeddedId
    private CampThemeId campThemeId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "themeCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_cd_id")
    private ThemeCode themeCode;

    @Transient
    private boolean isNew = false;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampThemeId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long themeCdId;
    }

    public static CampTheme createCampTheme(Camp camp, ThemeCode themeCode) {
        CampThemeId id = new CampThemeId(camp.getCpId(), themeCode.getThemeCdId());
        return new CampTheme(id, camp, themeCode, true);
    }

    @Override
    public CampThemeId getId() {
        return getCampThemeId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public String getCampCodeNm() {
        return themeCode.getThemeCdNm();
    }
}
