package com.project.camphub.controller.camp;

import com.project.camphub.domain.camp.dto.CampDto;
import com.project.camphub.common.dto.Response;
import com.project.camphub.service.camp.CampService;
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
public class CampController {

    private final CampService campService;

    @GetMapping("/{cpId}")
    public ResponseEntity<Response<CampDto>> findByCpId(@PathVariable("cpId") String cpId) {
        return ResponseEntity.ok(campService.findById(cpId));
    }
}
