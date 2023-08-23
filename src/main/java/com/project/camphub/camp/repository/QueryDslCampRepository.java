package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampRequestDto;
import com.project.camphub.camp.entity.Camp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface QueryDslCampRepository {

    Page<Camp> findCampList(SearchCampRequestDto searchCampRequestDto, Pageable pageable);
}
