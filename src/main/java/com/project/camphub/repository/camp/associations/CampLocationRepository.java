package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampLocationRepository extends JpaRepository<CampLocation, CampLocation.CampLocationId> {
}
