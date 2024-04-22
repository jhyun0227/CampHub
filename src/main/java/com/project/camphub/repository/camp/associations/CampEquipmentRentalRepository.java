package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampEquipmentRental;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampEquipmentRentalRepository extends JpaRepository<CampEquipmentRental, CampEquipmentRental.CampEquipmentRentalId> {

    @EntityGraph(attributePaths = {"equipmentCode"})
    List<CampEquipmentRental> findByCampEquipmentRentalId_CpId(String cpId);
}
