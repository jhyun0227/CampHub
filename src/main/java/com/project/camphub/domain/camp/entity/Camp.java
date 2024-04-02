package com.project.camphub.domain.camp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Camp {

    @Id
    @Column(length = 30)
    private String cpId;
    @Column(length = 50)
    private String cpName;
    @Column(length = 20)
    private String cpTel;
    @Column(length = 2000)
    private String cpHomepageUrl;
    @Column(length = 2000)
    private String cpResvUrl;
    @Column(length = 1000)
    private String cpThumbUrl;
    @Column(length = 10)
    private String cpProvCd;
    @Column(length = 10)
    private String cpDistCd;
    @Column(length = 50)
    private String cpZipcode;
    @Column(length = 200)
    private String cpAddr;
    @Column(length = 200)
    private String cpAddrDetail;

    private Double cpLon;
    private Double cpLat;

    @Column(length = 2000)
    private String cpDirections;
    @Column(length = 100)
    private String cpTourBizNo;
    @Column(length = 100)
    private String cpBizNo;

    private LocalDateTime cpPermitDt;
    private LocalDateTime cpCreateDt;
    private LocalDateTime cpModDt;

    @Column(length = 1)
    private Character cpIsActive;

}
