package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampTheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampThemeRepository extends JpaRepository<CampTheme, CampTheme.CampThemeId> {
}
