package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.entity.Camp;
import com.project.camphub.common.code.AreaCodeMap;
import com.project.camphub.common.code.EnvironmentMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class QueryDslCampRepositoryImplTest {

    @Autowired
    AreaCodeMap areaCodeMap;
    @Autowired
    EnvironmentMap environmentMap;

    @Autowired
    CampRepository campRepository;

    /**
     * 야영장명(cp_faclt_nm) 조건
     */
    @Test
    void findCampListFacltNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        searchCampListRequestDto.setFacltNm("씨앗골");
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(10);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpFacltNm = {}", camp.getCpFacltNm());
            assertThat(camp.getCpFacltNm()).contains("씨앗골");
        }
    }

    /**
     * 도 이름(cpd_do_nm), 시군구 이름(cpd_sigungu_nm) 조건
     */
    @Test
    void findCampListDoNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        searchCampListRequestDto.setDoCd("11");
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(10);

        searchCampListRequestDto.setDoNm(areaCodeMap.getDoMap().get(searchCampListRequestDto.getDoCd()));

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpdDoNm = {}", camp.getCampDetail().getCpdDoNm());
            assertThat(camp.getCampDetail().getCpdDoNm()).isEqualTo("충청북도");
        }
    }
    @Test
    void findCampListSigunguNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        searchCampListRequestDto.setDoCd("11");
        searchCampListRequestDto.setSigunguCd("1111");
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(10);

        searchCampListRequestDto.setDoNm(areaCodeMap.getDoMap().get(searchCampListRequestDto.getDoCd()));
        searchCampListRequestDto.setSigunguNm(areaCodeMap.getRelationMap().get(searchCampListRequestDto.getDoCd()).get(searchCampListRequestDto.getSigunguCd()));

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpdDoNm = {}, camp.getCpdSigungiNm = {}", camp.getCampDetail().getCpdDoNm(), camp.getCampDetail().getCpdSigunguNm());
            assertThat(camp.getCampDetail().getCpdSigunguNm()).isEqualTo("청주시");
        }
    }

    /**
     * 입지구분(cp_lct_cl) 조건
     */
    @Test
    void findCampListLctClNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        searchCampListRequestDto.setLctClCd("be");
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(10);

        searchCampListRequestDto.setLctClNm(environmentMap.getEnvironmentMap().get(searchCampListRequestDto.getLctClCd()));

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpLctCl = {}", camp.getCpLctCl());
            assertThat(camp.getCpLctCl()).contains("해변");
        }
    }
}