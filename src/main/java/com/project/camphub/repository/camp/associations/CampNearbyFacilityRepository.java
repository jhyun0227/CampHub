package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampNearbyFacility;
import com.project.camphub.domain.camp.entity.associations.id.CampNearbyFacilityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampNearbyFacilityRepository extends JpaRepository<CampNearbyFacility, CampNearbyFacilityId> {
}
