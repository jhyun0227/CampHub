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

    /**
     * persistable 구현메서드
     * isNew()를 무조건 true를 반환하는 이유
     * repository.save()를 호출하면 Entity의 PK 유무로 persist(), merge() 분기가 나뉜다.
     * Id를 수동으로 할당하는 방식을 사용하기 때문에 merge()를 호출하게 되는데 select 쿼리를 한번 DB에 전달하고
     * 없을 경우 insert 쿼리를 다시 전달하기 때문에 성능상으로 비효율적이게 된다.
     * Camp 관련 Entity들이 외부 API를 통해 데이터를 적재하는 것 외에 repository.save()를 호출하는 경우는 없을 것으로 판단된다.
     * 수정의 경우에는 더티 체킹을 사용하는것을 명심하도록 하자.
     */
    @Override
    public String getId() {
        return this.getCpId();
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public static CampDetail apiToEntity(OpenApiResponse.Item item, Camp camp) {
        return CampDetail.builder()
                .camp(camp)
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
                .build();
    }
}
