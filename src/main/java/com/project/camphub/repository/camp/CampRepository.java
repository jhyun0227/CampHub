package com.project.camphub.repository.camp;

import com.project.camphub.domain.camp.entity.Camp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampRepository extends JpaRepository<Camp, String> {
}
