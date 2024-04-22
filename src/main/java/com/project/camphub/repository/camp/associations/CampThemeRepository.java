package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampTheme;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampThemeRepository extends JpaRepository<CampTheme, CampTheme.CampThemeId> {

    @EntityGraph(attributePaths = {"themeCode"})
    List<CampTheme> findByCampThemeId_CpId(String cpId);
}
