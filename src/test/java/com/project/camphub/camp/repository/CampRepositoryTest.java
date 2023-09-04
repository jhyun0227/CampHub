package com.project.camphub.camp.repository;

import com.project.camphub.camp.entity.Camp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class CampRepositoryTest {

    @Autowired CampRepository campRepository;

    @Test
    void findByCpId() {
        String cpId = "0011729f-b870-4b6e-8481-84fa12181232";

        Camp camp = campRepository.findByCpId(cpId).get();
        log.info("camp = {}", camp);

        assertThat(camp.getCpFacltNm()).isEqualTo("에코그린캠핑장");
        assertThat(camp.getCampDetail()).isNotNull();
        assertThat(camp.getCampFacility()).isNotNull();
        assertThat(camp.getCampSite()).isNotNull();
    }
}