package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.enumeration.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class CampDetail {

    @Id
    @Column(length = 10)
    private String cpId;

    @MapsId(value = "cpId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @Column(length = 5000)
    private String cpdIntro;

    @Column(length = 500)
    private String cpdLineIntro;

    @Column(length = 2000)
    private String cpdFeatures;

    @Column(length = 2000)
    private String cpdTooltip;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private OperationDaysType cpdOperDaysType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ManagingStatusType cpdMngStatType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ManagingDivType cpdMngDivType;

    @Column(length = 50)
    private String cpdMngOrg;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private FacilityDivType cpdFcltDivType;

    private Integer cpdResStaff;

    private LocalDateTime cpdOffStartDt;
    private LocalDateTime cpdOffEndDt;

    @Column(length = 1)
    private String cpdCultEvYn;
    @Column(length = 200)
    private String cpdCultEvNm;
    @Column(length = 1)
    private String cpdExprPrgmYn;
    @Column(length = 200)
    private String cpdExprPrgmNm;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AnimalEntryType cpdAnimEntType;

    @Column(length = 1)
    private String cpdPrvtCrvYn;
    @Column(length = 1)
    private String cpdPrvtTrlYn;
    @Column(length = 1)
    private String cpdInsuredYn;
}
