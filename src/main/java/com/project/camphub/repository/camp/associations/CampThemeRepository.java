package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampReservation;
import com.project.camphub.domain.camp.entity.associations.id.CampReservationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampThemeRepository extends JpaRepository<CampReservation, CampReservationId> {
}
