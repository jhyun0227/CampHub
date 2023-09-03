package com.project.camphub.camp.controller;

import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.dto.SearchCampListResponseDto;
import com.project.camphub.camp.service.CampService;
import com.project.camphub.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/camp")
public class CampController {

    private final CampService campService;

    @GetMapping("/list")
    public ResponseDto findCampList(@RequestBody SearchCampListRequestDto searchCampListRequestDto) {
        Page<SearchCampListResponseDto> campList = campService.findCampList(searchCampListRequestDto);

        return ResponseDto.ok(campList);
    }
}
