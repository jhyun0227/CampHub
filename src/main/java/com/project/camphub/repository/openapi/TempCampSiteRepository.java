package com.project.camphub.repository.openapi;

import com.project.camphub.domain.openapi.entity.TempCamp;
import com.project.camphub.domain.openapi.entity.TempCampSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempCampSiteRepository extends JpaRepository<TempCampSite, String> {
}
