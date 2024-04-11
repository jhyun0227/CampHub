package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampOperationSeason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampOperationSeasonRepository extends JpaRepository<CampOperationSeason, CampOperationSeason.CampOperationSeasonId> {
}
