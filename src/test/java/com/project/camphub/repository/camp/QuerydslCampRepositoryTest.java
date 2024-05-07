package com.project.camphub.repository.camp;

import com.project.camphub.domain.camp.dto.CampSearchCondDto;
import com.project.camphub.domain.camp.entity.Camp;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class QuerydslCampRepositoryTest {

    @Autowired
    QuerydslCampRepository querydslCampRepository;
    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("캠핑장명 조건 조회")
    void findCampListByCpName() {
        CampSearchCondDto campSearchCondDto = new CampSearchCondDto();
        campSearchCondDto.setCpName("청춘");

        List<Camp> findCampList = querydslCampRepository.findCampListByCond(campSearchCondDto);

        Assertions.assertThat(findCampList.size()).isEqualTo(2);
    }

}