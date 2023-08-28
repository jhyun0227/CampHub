package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampRequestDto;
import com.project.camphub.camp.entity.Camp;
import com.project.camphub.common.code.AreaCodeMap;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class QueryDslCampRepositoryImplTest {

    @Autowired
    AreaCodeMap areaCodeMap;

    @Autowired
    CampRepository campRepository;

    /**
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
        Camp camp = camps.get(0);

        assertThat(camp.getCpFacltNm()).isEqualTo("씨앗골캠핑장");
    }

    /**
     * 도 이름(cpd_do_nm), 시군구 이름(cpd_sigungu_nm) 조건
     */
    @Test
    void findCampListDoNmCond() {
        SearchCampRequestDto searchCampRequestDto = new SearchCampRequestDto();
        searchCampRequestDto.setDoCd("11");
        searchCampRequestDto.setPage(0);
        searchCampRequestDto.setSize(10);

        searchCampRequestDto.setDoNm(areaCodeMap.getDoMap().get(searchCampRequestDto.getDoCd()));

        PageRequest pageRequest = PageRequest.of(searchCampRequestDto.getPage(), searchCampRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        Camp camp1 = camps.get(0);
        Camp camp2 = camps.get(1);
        Camp camp3 = camps.get(2);
        assertThat(camp1.getCampDetail().getCpdDoNm()).isEqualTo("충청북도");
        assertThat(camp2.getCampDetail().getCpdDoNm()).isEqualTo("충청북도");
        assertThat(camp3.getCampDetail().getCpdDoNm()).isEqualTo("충청북도");
    }
    @Test
    void findCampListSigunguNmCond() {
        SearchCampRequestDto searchCampRequestDto = new SearchCampRequestDto();
        searchCampRequestDto.setDoCd("11");
        searchCampRequestDto.setSigunguCd("1111");
        searchCampRequestDto.setPage(0);
        searchCampRequestDto.setSize(10);

        searchCampRequestDto.setDoNm(areaCodeMap.getDoMap().get(searchCampRequestDto.getDoCd()));
        searchCampRequestDto.setSigunguNm(areaCodeMap.getRelationMap().get(searchCampRequestDto.getDoCd()).get(searchCampRequestDto.getSigunguCd()));

        PageRequest pageRequest = PageRequest.of(searchCampRequestDto.getPage(), searchCampRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpdDoNm = {}, camp.getCpdSigungiNm = {}", camp.getCampDetail().getCpdDoNm(), camp.getCampDetail().getCpdSigunguNm());
        }

        Camp camp1 = camps.get(0);
        assertThat(camp1.getCampDetail().getCpdSigunguNm()).isEqualTo("청주시");
    }
}