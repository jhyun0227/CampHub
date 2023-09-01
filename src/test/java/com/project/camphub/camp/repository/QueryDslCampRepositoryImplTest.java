package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.entity.Camp;
import com.project.camphub.common.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class QueryDslCampRepositoryImplTest {

    @Autowired
    AreaCode areaCode;
    @Autowired
    LocationCode locationCode;
    @Autowired
    FacltDivCode facltDivCode;
    @Autowired
    IndutyCode indutyCode;
    @Autowired
    ThemaEnvironmentCode themaEnvironmentCode;

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
     * 도 이름 조건의 경우 복수 조회가 가능하다.
     * 시군구 이름 조건의 경우 도1, 시군구1로 조회가 가능하다.
     */
    @Test
    void findCampListDoNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        List<String> doCdList = searchCampListRequestDto.getDoCdList();
        doCdList.add("11");
        doCdList.add("12");
        log.info("doCdList = {}", doCdList);
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(100);

        List<String> doNmList = searchCampListRequestDto.getDoNmList();
        for (String doCd : doCdList) {
            doNmList.add(areaCode.getDoCodeMap().get(doCd));
        }
        log.info("doNmList = {}", doNmList);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpdDoNm = {}", camp.getCampDetail().getCpdDoNm());
            assertThat(camp.getCampDetail().getCpdDoNm()).isIn("충청북도", "충청남도");
        }
    }
    @Test
    void findCampListSigunguNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        List<String> doCdList = searchCampListRequestDto.getDoCdList();
        doCdList.add("11");
        log.info("doCdList = {}", doCdList);
        searchCampListRequestDto.setSigunguCd("1111");
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(100);

        List<String> doNmList = searchCampListRequestDto.getDoNmList();
        for (String doCd : doCdList) {
            doNmList.add(areaCode.getDoCodeMap().get(doCd));
            searchCampListRequestDto.setSigunguNm(areaCode.getRelationMap().get(doCd).get(searchCampListRequestDto.getSigunguCd()));
        }
        log.info("doNmList = {}", doNmList);
        log.info("sigunguNm = {}", searchCampListRequestDto.getSigunguNm());

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
        List<String> lctClCdList = searchCampListRequestDto.getLctClCdList();
        lctClCdList.add("be");
        lctClCdList.add("dt");
        log.info("lctClCdList = {}", lctClCdList);
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(100);

        List<String> lctClNmList = searchCampListRequestDto.getLctClNmList();
        for (String lctClCd : lctClCdList) {
            lctClNmList.add(locationCode.getLocationCodeMap().get(lctClCd));
        }
        log.info("lctClNmList = {}", lctClNmList);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpLctCl = {}", camp.getCpLctCl());
            assertThat(camp.getCpLctCl()).matches(s -> s.contains("해변") || s.contains("도심"));
        }
    }

    /**
     * 사업주체(cp_faclt_div_nm) 조건
     */
    @Test
    void findCampListFacltDivNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        List<String> facltDivCdList = searchCampListRequestDto.getFacltDivCdList();
        facltDivCdList.add("lg");
        facltDivCdList.add("np");
        facltDivCdList.add("nrf");
        log.info("facltDivCdList = {}", facltDivCdList);
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(100);

        List<String> facltDivNmList = searchCampListRequestDto.getFacltDivNmList();
        for (String facltDivCd : facltDivCdList) {
            facltDivNmList.add(facltDivCode.getFacltDivCodeMap().get(facltDivCd));
        }
        log.info("facltDivNmList = {}", facltDivNmList);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpFacltDivNm = {}", camp.getCpFacltDivNm());
            assertThat(camp.getCpFacltDivNm()).isIn("지자체", "국립공원", "자연휴양림");
        }
    }

    /**
     * 업종코드(cp_induty) 조건
     */
    @Test
    void findCampListIndutyNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        List<String> indutyCdList = searchCampListRequestDto.getIndutyCdList();
        indutyCdList.add("gnrl");
        indutyCdList.add("auto");
        log.info("indutyCdList = {}", indutyCdList);
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(100);

        List<String> indutyNmList = searchCampListRequestDto.getIndutyNmList();
        for (String indutyCd : indutyCdList) {
            indutyNmList.add(indutyCode.getIndutyCodeMap().get(indutyCd));
        }
        log.info("indutyNmList = {}", indutyNmList);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpInduty = {}", camp.getCpInduty());
            assertThat(camp.getCpInduty()).matches(s -> s.contains("일반야영장") || s.contains("자동차야영장"));
        }
    }

    /**
     * 사이트바닥(cps_site_bottom_cl[n]) 조건
     */
    @Test
    void findCampListSiteBottomCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        List<String> siteBottomCdList = searchCampListRequestDto.getSiteBottomCdList();
        siteBottomCdList.add("gr");
        siteBottomCdList.add("cs");
        siteBottomCdList.add("te");
        log.info("siteBottomCdList = {}", siteBottomCdList);
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(100);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("-----------------------------------------------------");
            log.info("camp.getCampSite().getCpsSiteBottomCl1() = {}", camp.getCampSite().getCpsSiteBottomCl1());
            log.info("camp.getCampSite().getCpsSiteBottomCl2() = {}", camp.getCampSite().getCpsSiteBottomCl2());
            log.info("camp.getCampSite().getCpsSiteBottomCl3() = {}", camp.getCampSite().getCpsSiteBottomCl3());
            log.info("-----------------------------------------------------");
            assertThat(camp.getCampSite())
                    .matches(campSite ->
                        !campSite.getCpsSiteBottomCl1().equals("0") || !campSite.getCpsSiteBottomCl2().equals("0") || !campSite.getCpsSiteBottomCl3().equals("0")
                    );
        }
    }

    /**
     * 테마환경(cp_thema_envrn_cl) 조건
     */
    @Test
    void findCampListThemaEnvironmentNmCond() {
        SearchCampListRequestDto searchCampListRequestDto = new SearchCampListRequestDto();
        List<String> themaEnvironmentCdList = searchCampListRequestDto.getThemaEnvironmentCdList();
        themaEnvironmentCdList.add("sr");
        themaEnvironmentCdList.add("ss");
        themaEnvironmentCdList.add("wl");
        themaEnvironmentCdList.add("al");
        themaEnvironmentCdList.add("sk");
        log.info("themaEnvironmentCdList = {}", themaEnvironmentCdList);
        searchCampListRequestDto.setPage(0);
        searchCampListRequestDto.setSize(100);

        List<String> themaEnvironmentNmList = searchCampListRequestDto.getThemaEnvironmentNmList();
        for (String themaEnvironmentCd : themaEnvironmentCdList) {
            themaEnvironmentNmList.add(themaEnvironmentCode.getThemaEnvironmentCodeMap().get(themaEnvironmentCd));
        }
        log.info("themaEnvironmentNmList = {}", themaEnvironmentNmList);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), searchCampListRequestDto.getSize());

        List<Camp> camps = campRepository.findCampList(searchCampListRequestDto, pageRequest).getContent();
        log.info("camps.size() = {}", camps.size());

        for (Camp camp : camps) {
            log.info("camp.getCpThemaEnvrnCl = {}", camp.getCpThemaEnvrnCl());
            assertThat(camp.getCpThemaEnvrnCl()).matches(s ->
                        s.contains("일출명소") || s.contains("일몰명소") || s.contains("수상레저") || s.contains("항공레저") || s.contains("스키")
                    );
        }
    }
}