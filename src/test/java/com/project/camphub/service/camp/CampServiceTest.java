package com.project.camphub.service.camp;

import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.domain.camp.dto.CampDto;
import com.project.camphub.exception.camp.CampNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class CampServiceTest {

    @Autowired
    CampService campService;

    @Test
    @DisplayName("캠프 단건 조회 성공")
    void findById_success() {
        String cpId = "10";

        ResponseDto<CampDto> responseDto = campService.findById(cpId);

        Assertions.assertThat(responseDto.code()).isEqualTo(200);

        CampDto campDto = responseDto.data();
        Assertions.assertThat(campDto.getCpId()).isEqualTo(cpId);
        Assertions.assertThat(campDto.getCpName()).isEqualTo("(주)아웃오브파크");
    }

    @Test
    @DisplayName("캠프 단건 조회 실패")
    void findById_fail() {
        String cpId = "123123";

        Assertions.assertThatThrownBy(() -> campService.findById(cpId))
                .isInstanceOf(CampNotFoundException.class);
    }
}