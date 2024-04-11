package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampAmenityRepository extends JpaRepository<CampAmenity, CampAmenity.CampAmenityId> {
}
