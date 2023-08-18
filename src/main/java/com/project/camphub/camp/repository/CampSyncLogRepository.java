package com.project.camphub.camp.repository;

import com.project.camphub.camp.entity.CampSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampSyncLogRepository extends JpaRepository<CampSyncLog, String> {
}
