package com.project.camphub.externalapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenApiServiceTest {

    @Autowired
    OpenApiService openApiService;

    @Test
    void getCampInfo() {
        openApiService.getCampInfo();
    }

}