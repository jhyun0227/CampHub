package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampRequestDto;
import com.project.camphub.camp.entity.Camp;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class QueryDslCampRepositoryImplTest {

    @Autowired
    CampRepository campRepository;

    /**
     * findCampList 메서드 테스트 파일
     * 야영장명(cp_faclt_nm) 조건
     */
    @Test
    void findCampListFacltNmCond() {
        SearchCampRequestDto searchCampRequestDto = new SearchCampRequestDto();
        searchCampRequestDto.setFacltNm("씨앗골");
        searchCampRequestDto.setPage(0);
        searchCampRequestDto.setSize(10);

        PageRequest pageRequest = PageRequest.of(searchCampRequestDto.getPage(), searchCampRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampRequestDto, pageRequest).getContent();

        Assertions.assertThat(camps.get(0).getCpFacltNm()).isEqualTo("씨앗골캠핑장");
    }
}