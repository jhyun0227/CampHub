package com.project.camphub.controller.openapi;

import com.project.camphub.service.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/openapi")
public class OpenApiController {

    private final OpenApiService openApiService;

    /**
     * 공공 데이터 포털을 통해서 모든 캠핑장을 조회 후 데이터베이스에 저장
     */
    @GetMapping("/init")
    public ResponseEntity<Map<String, Object>> initializeCampList() {
        openApiService.initializeCampList();

        Map<String, Object> result = new HashMap<>();
        return ResponseEntity.ok()
                .body(result);
    }

}
