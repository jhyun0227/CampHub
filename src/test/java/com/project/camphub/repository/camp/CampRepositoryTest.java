package com.project.camphub.repository.camp;

import com.project.camphub.domain.camp.entity.Camp;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    void findCampsByCpIdIn() {
        List<String> cpIdList = new ArrayList<>();
        cpIdList.add("10");
        cpIdList.add("100000");
        cpIdList.add("100001");

        List<Camp> findCampList = campRepository.findCampsByCpIdIn(cpIdList);

        Assertions.assertThat(3).isEqualTo(findCampList.size());
    }

}