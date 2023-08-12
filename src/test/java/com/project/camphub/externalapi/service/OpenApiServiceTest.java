package com.project.camphub.externalapi.service;

import com.project.camphub.camp.entity.Camp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class OpenApiServiceTest {

    @Autowired
    OpenApiService openApiService;

    @Test
    @Commit
    void CampInfo() {
        List<Camp> camps = openApiService.campInfo();

        Assertions.assertThat(camps.size()).isEqualTo(10);
    }

}