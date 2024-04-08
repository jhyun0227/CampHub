package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampTheme;
import com.project.camphub.domain.camp.entity.associations.id.CampThemeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampThemeRepository extends JpaRepository<CampTheme, CampThemeId> {
}
