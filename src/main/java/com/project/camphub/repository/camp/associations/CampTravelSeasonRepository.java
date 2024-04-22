package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampTravelSeason;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampTravelSeasonRepository extends JpaRepository<CampTravelSeason, CampTravelSeason.CampTravelSeasonId> {

    @EntityGraph(attributePaths = {"seasonCode"})
    List<CampTravelSeason> findByCampTravelSeasonId_CpId(String cpId);
}
