package com.project.camphub.repository.camp;

import com.project.camphub.domain.camp.entity.Camp;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampRepository extends JpaRepository<Camp, String> {
    @EntityGraph(attributePaths = {"campDetail", "campFacility", "campSite"})
    List<Camp> findCampsByCpIdIn(List<String> cpIdList);

    @Query("select c from Camp c " +
            "join fetch c.campDetail cd " +
            "join fetch c.campFacility cf " +
            "join fetch c.campSite cs " +
            "join fetch c.campAmenityList cal " +
            "join fetch c.campCaravanInnerAmenityList ccial " +
            "join fetch c.campEquipmentRentalList cerl " +
            "join fetch c.campGlampingInnerAmenityList cgial " +
            "join fetch c.campIndustryList cil " +
            "join fetch c.campLocationList cll " +
            "join fetch c.campNearbyFacilityList cnfl " +
            "join fetch c.campOperationSeasonList cosl " +
            "join fetch c.campReservationList crl " +
            "join fetch c.campThemeList ctl " +
            "join fetch c.campTravelSeasonList ctsl " +
            "where c.cpId in :cpIdList")
    List<Camp> findAllAssociationsByCpIdIn(@Param("cpIdList") List<String> cpIdList);
}
