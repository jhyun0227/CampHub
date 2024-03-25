package com.project.camphub.domain.openapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 코드화를 위한 임시 테이블,
 * 코드 정리 후 삭제 예정
 */
@Data
@Entity
public class TempCampFacility {

    @Id
    private String contentId;
    private String manageNmpr;
    private String allar;
    private String sbrsCl;
    private String sbrsEtc;
    private String posblFcltyCl;
    private String posblFcltyEtc;
    private String gnrlSiteCo;
    private String autoSiteCo;
    private String glampSiteCo;
    private String caravSiteCo;
    private String indvdlCaravSiteCo;
    private String glampInnerFclty;
    private String caravInnerFclty;
    private String brazierCl;
    private String wtrplCo;
    private String toiletCo;
    private String swrmCo;
    private String extshrCo;
    private String frprvtWrppCo;
    private String frprvtSandCo;
    private String fireSensorCo;

}
