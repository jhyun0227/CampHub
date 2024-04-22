package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampNearbyFacility;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampNearbyFacilityRepository extends JpaRepository<CampNearbyFacility, CampNearbyFacility.CampNearbyFacilityId> {

    @EntityGraph(attributePaths = {"nearbyFacilityCode"})
    List<CampNearbyFacility> findByCampNearbyFacilityId_CpId(String cpId);
}
