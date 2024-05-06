package com.project.camphub.controller.camp;

import com.project.camphub.domain.camp.dto.CampDto;
import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.domain.camp.dto.CampListDto;
import com.project.camphub.domain.camp.dto.CampSearchCondDto;
import com.project.camphub.service.camp.CampService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/camp")
@Tag(name = "Camp", description = "캠핑장 API")
public class CampController {

    private final CampService campService;

    @GetMapping("/{cpId}")
    @Operation(summary = "캠프 단건 조회", description = "캠프ID로 캠프 단건을 조회하는 API")
    public ResponseEntity<ResponseDto<CampDto>> findCampByCpId(
            @Parameter(description = "캠프ID", example = "10", in = ParameterIn.PATH) @PathVariable("cpId") String cpId) {
        return ResponseEntity.ok(campService.findCampById(cpId));
    }

    @GetMapping("/list")
    @Operation(
            summary = "캠프 목록 조회", description = "요청 조건으로 부합되는 캠프 목록을 조회하는 API",
            parameters = {
                    @Parameter(name = "cpName", description = "캠핑장명", example = "청춘", in = ParameterIn.QUERY)
            }
    )
    public ResponseEntity<ResponseDto<List<CampListDto>>> findCampListByCond(
            @Parameter(hidden = true) @ModelAttribute CampSearchCondDto campSearchCondDto) {
        return ResponseEntity.ok(campService.findCampListByCond(campSearchCondDto));
    }
}
