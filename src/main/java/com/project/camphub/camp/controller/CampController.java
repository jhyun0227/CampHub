package com.project.camphub.camp.controller;

import com.project.camphub.camp.dto.CampDto;
import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.dto.SearchCampListResponseDto;
import com.project.camphub.camp.service.CampService;
import com.project.camphub.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/camp")
public class CampController {

    private final CampService campService;

    /**
     * 캠프 리스트 조회
     */
    @GetMapping("/list")
    public ResponseDto findCampList(@RequestBody SearchCampListRequestDto searchCampListRequestDto) {
        Page<SearchCampListResponseDto> campList = campService.findCampList(searchCampListRequestDto);

        return ResponseDto.ok(campList);
    }

    /**
     * 캠프 단건 조회
     */
    @GetMapping("/{cpId}")
    public ResponseDto findCampInfo(@PathVariable String cpId) {
        CampDto campDto = campService.findCampInfo(cpId);

        return ResponseDto.ok(campDto);
    }
}
