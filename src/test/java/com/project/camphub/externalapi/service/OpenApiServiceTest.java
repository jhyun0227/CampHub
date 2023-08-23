package com.project.camphub.externalapi.service;

import com.project.camphub.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class OpenApiServiceTest {

    @Autowired
    OpenApiService openApiService;

    @Test
    @Commit
    void campInfo() {
        ResponseDto responseDto = openApiService.campInfo();

        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Commit
    void campSyncInfo() {
        ResponseDto responseDto = openApiService.campSyncInfo();

        log.info("responseDto = {}", responseDto);

        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

}