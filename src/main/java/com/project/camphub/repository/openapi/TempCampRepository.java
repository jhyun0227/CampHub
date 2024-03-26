package com.project.camphub.repository.openapi;

import com.project.camphub.domain.openapi.entity.TempCamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempCampRepository extends JpaRepository<TempCamp, String> {
}
