package com.project.camphub.camp.repository;

import com.project.camphub.camp.entity.Camp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CampRepository extends JpaRepository<Camp, String> {

    //contentId들로 Camp 정보를 Fetch 전부 가져오는 메서드
    @Query("select c from Camp c left join fetch c.campDetail left join fetch c.campFacility left join fetch c.campSite where c.cpContentId in :contentsIds")
    List<Camp> findCampFetchAll(@Param("contentsIds") Set<String> contentsIds);

}
