package com.project.camphub.domain.camp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Camp {

    @Id
    @Column(length = 10)
    private String cpId;
    @Column(length = 100)
    private String cpName;
    @Column(length = 30)
    private String cpTel;
    @Column(length = 2000)
    private String cpHomepageUrl;
    @Column(length = 2000)
    private String cpResvUrl;
    @Column(length = 500)
    private String cpThumbUrl;
    @Column(length = 2)
    private String cpProvCd;
    @Column(length = 2)
    private String cpDistCd;
    @Column(length = 20)
    private String cpZipcode;
    @Column(length = 100)
    private String cpAddr;
    @Column(length = 100)
    private String cpAddrDetail;

    private Double cpLon;
    private Double cpLat;

    @Column(length = 1000)
    private String cpDirections;
    @Column(length = 50)
    private String cpTourBizNo;
    @Column(length = 50)
    private String cpBizNo;

    private LocalDateTime cpPermitDt;
    private LocalDateTime cpCreateDt;
    private LocalDateTime cpModDt;

    @Column(length = 1)
    private String cpIsActive;

    //캠프 상세, 시설, 사이트
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampDetail campDetail;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampFacility campFacility;
    @OneToOne(mappedBy = "camp", fetch = FetchType.LAZY)
    private CampSite campSite;

    //캠프 <-> 코드 관련
}
