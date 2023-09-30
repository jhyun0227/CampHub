package com.project.camphub.camp.controller;

import com.project.camphub.camp.dto.CampDto;
import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.dto.SearchCampListResponseDto;
import com.project.camphub.camp.service.CampService;
import com.project.camphub.common.code.AreaCode;
import com.project.camphub.common.code.LocationCode;
import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.login.resolver.Login;
import com.project.camphub.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.TreeMap;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/camp")
public class CampController {

    private final CampService campService;

    private final AreaCode areaCode;
    private final LocationCode locationCode;

    /**
     * 캠프 리스트 Form
     */
    @GetMapping("/list")
    public String listForm(@Login Member member, @ModelAttribute SearchCampListRequestDto searchCampListRequestDto, Model model) {
        //도 정보
        model.addAttribute("doMap", new TreeMap<>(areaCode.getDoCodeMap()));
        //지역 환경 정보
        model.addAttribute("locationMap", locationCode.getLocationCodeMap());
        //회원
        model.addAttribute("member", member);
        //캠핑장정보
        model.addAttribute("result", campService.findCampList(searchCampListRequestDto));


        return "camp/list";
    }

    /**
     * 캠프 리스트 조회
     */
    @GetMapping("/listdd")
    @ResponseBody
    public ResponseDto findCampList(@RequestBody SearchCampListRequestDto searchCampListRequestDto) {
        Page<SearchCampListResponseDto> campList = campService.findCampList(searchCampListRequestDto);

        return ResponseDto.ok(campList);
    }

    /**
     * 캠프 단건 조회
     */
    @GetMapping("/{cpId}")
    @ResponseBody
    public ResponseDto findCampInfo(@PathVariable String cpId) {
        CampDto campDto = campService.findCampInfo(cpId);

        return ResponseDto.ok(campDto);
    }
}
