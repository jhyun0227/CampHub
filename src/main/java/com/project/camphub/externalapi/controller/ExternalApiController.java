package com.project.camphub.externalapi.controller;

import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.externalapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/external")
public class ExternalApiController {

    private final OpenApiService openApiService;

    /**
     * OpenApi에서 데이터를 가져와 DB에 저장하는 메서드
     */
    @PostMapping("/openapi/campinfo/base")
    public ResponseDto campInfo() {
        return openApiService.campInfo();
    }

    /**
     * OpenApi의 테이터와 DB의 데이터를 동기화하는 메서드
     */
    @PatchMapping("/openapi/campinfo/sync")
    public ResponseDto dd() {
        return openApiService.campSyncInfo();
    }
}
