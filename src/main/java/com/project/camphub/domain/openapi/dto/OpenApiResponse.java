package com.project.camphub.domain.openapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.project.camphub.config.jackson.ItemsDeserializer;
import lombok.Data;

import java.util.List;

/**
 * 내부 static class에 관해서 조금 더 공부가 필요할 것 같다.
 * 필드 or 메서드 static vs 클래스 static 차이에 대해서
 *
 * static 필드와 메서드는 클래스 레벨에서 작동하며, 클래스의 모든 인스턴스 간에 공유, 이는 주로 공통의 상수나 유틸리티 함수 구현에 사용
 * static 내부 클래스는 외부 클래스와 논리적으로는 관련되어 있지만, 실행 시 외부 클래스의 인스턴스와 독립적입니다. 이는 내부 클래스가 외부 클래스의 인스턴스 없이도 사용될 수 있음을 의미합니다.
 */
@Data
public class OpenApiResponse {

    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Data
    @JsonDeserialize(using = ItemsDeserializer.class)
    public static class Items {
        private List<Item> item;
    }

    @Data
    public static class Item {
        private String contentId;
        private String facltNm;
        private String tel;
        private String homepage;
        private String resveUrl;
        private String resveCl;
        private String firstImageUrl;
        private String doNm;
        private String sigunguNm;
        private String zipcode;
        private String addr1;
        private String addr2;
        private String mapX;
        private String mapY;
        private String direction;
        private String trsagntNo;
        private String bizrno;
        private String prmisnDe;
        private String createdtime;
        private String modifiedtime;
        private String syncStatus;

        private String intro;
        private String lineIntro;
        private String featureNm;
        private String tooltip;
        private String induty;
        private String lctCl;
        private String themaEnvrnCl;
        private String tourEraCl;
        private String operPdCl;
        private String operDeCl;
        private String manageSttus;
        private String mangeDivNm;
        private String mgcDiv;
        private String facltDivNm;
        private String manageNmpr;
        private String hvofBgnde;
        private String hvofEnddle;
        private String clturEventAt;
        private String clturEvent;
        private String exprnProgrmAt;
        private String exprnProgrm;
        private String eqpmnLendCl;
        private String animalCmgCl;
        private String caravAcmpnyAt;
        private String trlerAcmpnyAt;
        private String insrncAt;

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

        private String sitedStnc;
        private String siteMg1Co;
        private String siteMg2Co;
        private String siteMg3Co;
        private String siteMg1Width;
        private String siteMg1Vrticl;
        private String siteMg2Width;
        private String siteMg2Vrticl;
        private String siteMg3Width;
        private String siteMg3Vrticl;
        private String siteBottomCl1;
        private String siteBottomCl2;
        private String siteBottomCl3;
        private String siteBottomCl4;
        private String siteBottomCl5;
    }
}
