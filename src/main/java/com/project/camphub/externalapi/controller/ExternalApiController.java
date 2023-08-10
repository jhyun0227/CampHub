package com.project.camphub.externalapi.controller;

import com.project.camphub.externalapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/external")
public class ExternalApiController {

    private final OpenApiService openApiService;

    @GetMapping("/campinfo")
    public String saveCampInfo() {
        return "ok";
    }
}
