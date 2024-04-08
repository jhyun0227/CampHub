package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampThemeId;
import com.project.camphub.domain.camp.entity.code.ThemeCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampTheme {

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

    public CampTheme(Camp camp, ThemeCode themeCode) {
        this.camp = camp;
        this.themeCode = themeCode;
    }
}
