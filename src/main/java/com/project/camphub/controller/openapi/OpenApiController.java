package com.project.camphub.controller.openapi;

import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.service.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseDto<Void>> initializeCampList() {
        return ResponseEntity.ok(openApiService.initializeCampList());
    }

    /**
     * 공공 데이터 포털을 통해서 입력 날짜를 기준으로 데이터를 동기화
     * 연월(6자리), 연월일(8자리)
     */
    @GetMapping("/refresh")
    public ResponseEntity<ResponseDto<Void>> refreshCampList(@RequestParam("refreshDate") String refreshDate) {
        return ResponseEntity.ok(openApiService.refreshCampListFromAPI(refreshDate));
    }

}
