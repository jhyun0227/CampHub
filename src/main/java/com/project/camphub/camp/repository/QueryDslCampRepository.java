package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.entity.Camp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface QueryDslCampRepository {

    Page<Camp> findCampList(SearchCampListRequestDto searchCampListRequestDto, Pageable pageable);
}
