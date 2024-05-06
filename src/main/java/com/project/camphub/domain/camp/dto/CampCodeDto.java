package com.project.camphub.domain.camp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "캠프코드 DTO")
public class CampCodeDto {

    @Schema(description = "캠프코드ID")
    private Long codeId;
    @Schema(description = "캠프코드명")
    private String codeName;

}