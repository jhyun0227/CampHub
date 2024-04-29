package com.project.camphub.repository.camp;

import com.project.camphub.domain.camp.entity.Camp;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Transactional
@SpringBootTest
class CampRepositoryTest {

    @Autowired
    CampRepository campRepository;
    @Autowired
    EntityManagerFactory emf;

    @Test
    void findByCpId() {
        Camp camp = campRepository.findByCpId("10").get();
        Assertions.assertThat(camp.getCpId()).isEqualTo("10");
        Assertions.assertThat(camp.getCpName()).isEqualTo("(주)아웃오브파크");

        PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
        Assertions.assertThat(persistenceUnitUtil.isLoaded(camp.getCampDetail())).isTrue();
        Assertions.assertThat(persistenceUnitUtil.isLoaded(camp.getCampFacility())).isTrue();
        Assertions.assertThat(persistenceUnitUtil.isLoaded(camp.getCampSite())).isTrue();
    }

    @Test
    void findCampsByCpIdIn() {
        List<String> cpIdList = Arrays.asList("10", "100000", "100001");

        List<Camp> campList = campRepository.findCampsByCpIdIn(cpIdList);
        Assertions.assertThat(campList.size()).isEqualTo(3);

        Camp camp = campList.get(0);
        PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
        Assertions.assertThat(persistenceUnitUtil.isLoaded(camp.getCampDetail())).isTrue();
        Assertions.assertThat(persistenceUnitUtil.isLoaded(camp.getCampFacility())).isTrue();
        Assertions.assertThat(persistenceUnitUtil.isLoaded(camp.getCampSite())).isTrue();
    }

}