package com.project.camphub.repository.camp;

import com.project.camphub.domain.camp.entity.Camp;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CampRepository extends JpaRepository<Camp, String> {

    @EntityGraph(attributePaths = {"campDetail", "campFacility", "campSite"})
    Optional<Camp> findByCpId(String cpId);

    @EntityGraph(attributePaths = {"campDetail", "campFacility", "campSite"})
    List<Camp> findCampsByCpIdIn(List<String> cpIdList);
}
