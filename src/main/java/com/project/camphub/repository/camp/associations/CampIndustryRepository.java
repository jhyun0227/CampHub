package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampIndustry;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampIndustryRepository extends JpaRepository<CampIndustry, CampIndustry.CampIndustryId> {

    @EntityGraph(attributePaths = {"industryCode"})
    List<CampIndustry> findByCampIndustryId_CpId(String cpId);
}
