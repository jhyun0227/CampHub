package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampNearbyFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampNearbyFacilityRepository extends JpaRepository<CampNearbyFacility, CampNearbyFacility.CampNearbyFacilityId> {
}
