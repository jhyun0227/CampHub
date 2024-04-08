package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampAmenity;
import com.project.camphub.domain.camp.entity.associations.id.CampAmenityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampAmenityRepository extends JpaRepository<CampAmenity, CampAmenityId> {
}
