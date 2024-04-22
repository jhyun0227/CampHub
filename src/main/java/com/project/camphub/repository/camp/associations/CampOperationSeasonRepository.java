package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampOperationSeason;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampOperationSeasonRepository extends JpaRepository<CampOperationSeason, CampOperationSeason.CampOperationSeasonId> {

    @EntityGraph(attributePaths = {"seasonCode"})
    List<CampOperationSeason> findByCampOperationSeasonId_CpId(String cpId);
}
