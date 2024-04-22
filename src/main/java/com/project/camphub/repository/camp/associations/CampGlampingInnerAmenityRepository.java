package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampGlampingInnerAmenity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampGlampingInnerAmenityRepository extends JpaRepository<CampGlampingInnerAmenity, CampGlampingInnerAmenity.CampGlampingInnerAmenityId> {

    @EntityGraph(attributePaths = {"innerAmenityCode"})
    List<CampGlampingInnerAmenity> findByCampGlampingInnerAmenityId_CpId(String cpId);
}
