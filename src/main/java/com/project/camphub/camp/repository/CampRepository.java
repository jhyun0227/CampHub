package com.project.camphub.camp.repository;

import com.project.camphub.camp.entity.Camp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampRepository extends JpaRepository<Camp, String> {
}
