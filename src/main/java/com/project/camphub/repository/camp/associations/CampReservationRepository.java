package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampReservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampReservationRepository extends JpaRepository<CampReservation, CampReservation.CampReservationId> {

    @EntityGraph(attributePaths = {"reservationCode"})
    List<CampReservation> findByCampReservationId_CpId(String cpId);
}
