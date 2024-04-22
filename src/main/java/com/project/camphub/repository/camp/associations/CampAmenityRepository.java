package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampAmenity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampAmenityRepository extends JpaRepository<CampAmenity, CampAmenity.CampAmenityId> {

    @EntityGraph(attributePaths = {"amenityCode"})
    List<CampAmenity> findByCampAmenityId_CpId(String cpId);
}
