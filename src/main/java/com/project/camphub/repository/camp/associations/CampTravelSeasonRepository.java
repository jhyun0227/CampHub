package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampTravelSeason;
import com.project.camphub.domain.camp.entity.associations.id.CampTravelSeasonId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampTravelSeasonRepository extends JpaRepository<CampTravelSeason, CampTravelSeasonId> {
}
