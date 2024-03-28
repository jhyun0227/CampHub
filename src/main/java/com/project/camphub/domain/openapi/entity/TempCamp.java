package com.project.camphub.domain.openapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/**
 * 코드화를 위한 임시 테이블,
 * 코드 정리 후 삭제 예정
 */
@Data
@Entity
public class TempCamp {

    @Id
    private String contentId;
    private String facltNm;
    private String tel;
    @Column(length = 500)
    private String homepage;
    @Column(length = 2000)
    private String resveUrl;
    private String resveCl;
    @Column(length = 500)
    private String firstImageUrl;
    private String doNm;
    private String sigunguNm;
    private String zipcode;
    private String addr1;
    private String addr2;
    private String mapX;
    private String mapY;
    @Column(length = 2000)
    private String direction;
    private String trsagntNo;
    private String bizrno;
    private String prmisnDe;
    private String createdtime;
    private String modifiedtime;
    private String syncStatus;
}
