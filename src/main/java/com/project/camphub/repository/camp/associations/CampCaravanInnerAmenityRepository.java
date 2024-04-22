package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.associations.CampCaravanInnerAmenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampCaravanInnerAmenityRepository extends JpaRepository<CampCaravanInnerAmenity, CampCaravanInnerAmenity.CampCaravanInnerAmenityId> {

    List<CampCaravanInnerAmenity> findByCampCaravanInnerAmenityId_CpId(String cpId);
}
