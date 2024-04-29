package com.project.camphub.controller.camp;

import com.project.camphub.domain.camp.dto.CampDto;
import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.service.camp.CampService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/camp")
@Tag(name = "Camp", description = "캠핑장 API")
public class CampController {

    private final CampService campService;

    @GetMapping("/{cpId}")
    @Operation(summary = "캠프 단건 조회", description = "캠프ID로 캠프 단건을 조회하는 API")
    public ResponseEntity<ResponseDto<CampDto>> findByCpId(
            @Parameter(description = "캠프ID", in = ParameterIn.PATH) @PathVariable("cpId") String cpId) {
        return ResponseEntity.ok(campService.findById(cpId));
    }
}
