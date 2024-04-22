package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.enumeration.*;
import com.project.camphub.domain.common.enumaration.YnType;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

import static com.project.camphub.common.utils.DateUtils.parseStringToLocalDateTime;
import static java.lang.Integer.parseInt;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class CampDetail implements Persistable<String> {

    @Id
    @Column(length = 10)
    private String cpId;

    @MapsId
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

    private Integer cpdResStaffCnt;

    private LocalDateTime cpdOffStartDt;
    private LocalDateTime cpdOffEndDt;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType cpdCultEvYn;
    @Column(length = 200)
    private String cpdCultEvNm;
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType cpdExprPrgmYn;
    @Column(length = 200)
    private String cpdExprPrgmNm;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AnimalEntryType cpdAnimEntType;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType cpdPrvtCrvYn;
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType cpdPrvtTrlYn;
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YnType cpdInsuredYn;

    @Transient
    private boolean isNew = false;

    @Override
    public String getId() {
        return this.getCpId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public static CampDetail apiToEntity(OpenApiResponse.Item item) {
        return CampDetail.builder()
                .cpdIntro(item.getIntro())
                .cpdLineIntro(item.getLineIntro())
                .cpdFeatures(item.getFeatureNm())
                .cpdTooltip(item.getTooltip())
                .cpdOperDaysType(OperationDaysType.findByDescription(item.getOperDeCl()))
                .cpdMngStatType(ManagingStatusType.findByDescription(item.getManageSttus()))
                .cpdMngDivType(ManagingDivType.findByDescription(item.getMangeDivNm()))
                .cpdMngOrg(item.getMgcDiv())
                .cpdFcltDivType(FacilityDivType.findByDescription(item.getFacltDivNm()))
                .cpdResStaffCnt(parseInt(item.getManageNmpr()))
                .cpdOffStartDt(parseStringToLocalDateTime(item.getHvofBgnde()))
                .cpdOffEndDt(parseStringToLocalDateTime(item.getHvofEnddle()))
                .cpdCultEvYn(YnType.findByDescription(item.getClturEventAt()))
                .cpdCultEvNm(item.getClturEvent())
                .cpdExprPrgmYn(YnType.findByDescription(item.getExprnProgrmAt()))
                .cpdExprPrgmNm(item.getExprnProgrm())
                .cpdAnimEntType(AnimalEntryType.findByDescription(item.getAnimalCmgCl()))
                .cpdPrvtCrvYn(YnType.findByDescription(item.getCaravAcmpnyAt()))
                .cpdPrvtTrlYn(YnType.findByDescription(item.getTrlerAcmpnyAt()))
                .cpdInsuredYn(YnType.findByDescription(item.getInsrncAt()))
                .isNew(true)
                .build();
    }

    public void updateCampDetail(OpenApiResponse.Item item) {
        this.cpdIntro = item.getIntro();
        this.cpdLineIntro = item.getLineIntro();
        this.cpdFeatures = item.getFeatureNm();
        this.cpdTooltip = item.getTooltip();
        this.cpdOperDaysType = OperationDaysType.findByDescription(item.getOperDeCl());
        this.cpdMngStatType = ManagingStatusType.findByDescription(item.getManageSttus());
        this.cpdMngDivType = ManagingDivType.findByDescription(item.getMangeDivNm());
        this.cpdMngOrg = item.getMgcDiv();
        this.cpdFcltDivType = FacilityDivType.findByDescription(item.getFacltDivNm());
        this.cpdResStaffCnt = parseInt(item.getManageNmpr());
        this.cpdOffStartDt = parseStringToLocalDateTime(item.getHvofBgnde());
        this.cpdOffEndDt = parseStringToLocalDateTime(item.getHvofEnddle());
        this.cpdCultEvYn = YnType.findByDescription(item.getClturEventAt());
        this.cpdCultEvNm = item.getClturEvent();
        this.cpdExprPrgmYn = YnType.findByDescription(item.getExprnProgrmAt());
        this.cpdExprPrgmNm = item.getExprnProgrm();
        this.cpdAnimEntType = AnimalEntryType.findByDescription(item.getAnimalCmgCl());
        this.cpdPrvtCrvYn = YnType.findByDescription(item.getCaravAcmpnyAt());
        this.cpdPrvtTrlYn = YnType.findByDescription(item.getTrlerAcmpnyAt());
        this.cpdInsuredYn = YnType.findByDescription(item.getInsrncAt());
    }

    public void linkToCamp(Camp camp) {
        this.camp = camp;
        camp.linkToCampDetail(this);
    }
}
