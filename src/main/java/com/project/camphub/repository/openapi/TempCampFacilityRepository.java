package com.project.camphub.repository.openapi;

import com.project.camphub.domain.openapi.entity.TempCampDetail;
import com.project.camphub.domain.openapi.entity.TempCampFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempCampFacilityRepository extends JpaRepository<TempCampFacility, String> {
}
