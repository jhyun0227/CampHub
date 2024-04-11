package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampEquipmentRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampEquipmentRentalRepository extends JpaRepository<CampEquipmentRental, CampEquipmentRental.CampEquipmentRentalId> {
}
