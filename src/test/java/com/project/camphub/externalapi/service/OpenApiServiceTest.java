package com.project.camphub.externalapi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class OpenApiServiceTest {

    @Autowired
    OpenApiService openApiService;

    @Test
    @Commit
    void campInfo() {
        int totalCount = openApiService.campInfo();

        log.info("totalCount = {}", totalCount);
    }

    @Test
    @Commit
    void campSyncInfo() {
        String message = openApiService.campSyncInfo();

        log.info("message = {}", message);
    }

}