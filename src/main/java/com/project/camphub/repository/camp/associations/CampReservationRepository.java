package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampReservationRepository extends JpaRepository<CampReservation, CampReservation.CampReservationId> {
}
