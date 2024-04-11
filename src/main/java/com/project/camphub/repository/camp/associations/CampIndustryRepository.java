package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampIndustry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampIndustryRepository extends JpaRepository<CampIndustry, CampIndustry.CampIndustryId> {
}
