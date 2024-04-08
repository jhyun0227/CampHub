package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampIndustry;
import com.project.camphub.domain.camp.entity.associations.id.CampIndustryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampIndustryRepository extends JpaRepository<CampIndustry, CampIndustryId> {
}
