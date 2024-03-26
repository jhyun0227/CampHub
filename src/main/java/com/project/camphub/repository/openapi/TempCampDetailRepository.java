package com.project.camphub.repository.openapi;

import com.project.camphub.domain.openapi.entity.TempCamp;
import com.project.camphub.domain.openapi.entity.TempCampDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempCampDetailRepository extends JpaRepository<TempCampDetail, String> {
}
