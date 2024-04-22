package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampLocation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampLocationRepository extends JpaRepository<CampLocation, CampLocation.CampLocationId> {

    @EntityGraph(attributePaths = {"locationCode"})
    List<CampLocation> findByCampLocationId_CpId(String cpId);
}