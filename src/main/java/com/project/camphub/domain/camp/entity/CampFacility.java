package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.camp.enumeration.BrazierType;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import static java.lang.Integer.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class CampFacility implements Persistable<String> {

    @Id
    @Column(length = 10)
    private String cpId;

//    @MapsId(value = "cpId")
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    private Integer cpfTotalArea;

    @Column(length = 300)
    private String cpfAmntyEtc;
    @Column(length = 300)
    private String cpfNrbyFcltEtc;

    private Integer cpfGnrlSiteCnt;
    private Integer cpfCarSiteCnt;
    private Integer cpfGlampSiteCnt;
    private Integer cpfCrvSiteCnt;
    private Integer cpfPrvtCrvSiteCnt;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private BrazierType cpfBrazierType;

    private Integer cpfSinkCnt;
    private Integer cpfToiletCnt;
    private Integer cpfSwrmCnt;
    private Integer cpfFireExtCnt;
    private Integer cpfFireWaterCnt;
    private Integer cpfFireSandCnt;
    private Integer cpfFireSensorCnt;

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

    public static CampFacility apiToEntity(OpenApiResponse.Item item, Camp camp) {
        return CampFacility.builder()
                .camp(camp)
                .cpfTotalArea(parseInt(item.getAllar()))
                .cpfAmntyEtc(item.getSbrsEtc())
                .cpfNrbyFcltEtc(item.getPosblFcltyEtc())
                .cpfGnrlSiteCnt(parseInt(item.getGnrlSiteCo()))
                .cpfCarSiteCnt(parseInt(item.getAutoSiteCo()))
                .cpfGlampSiteCnt(parseInt(item.getGlampSiteCo()))
                .cpfCrvSiteCnt(parseInt(item.getCaravSiteCo()))
                .cpfPrvtCrvSiteCnt(parseInt(item.getIndvdlCaravSiteCo()))
                .cpfBrazierType(BrazierType.findByDescription(item.getBrazierCl()))
                .cpfSinkCnt(parseInt(item.getWtrplCo()))
                .cpfToiletCnt(parseInt(item.getToiletCo()))
                .cpfSwrmCnt(parseInt(item.getSwrmCo()))
                .cpfFireExtCnt(parseInt(item.getExtshrCo()))
                .cpfFireWaterCnt(parseInt(item.getFrprvtWrppCo()))
                .cpfFireSandCnt(parseInt(item.getFrprvtSandCo()))
                .cpfFireSensorCnt(parseInt(item.getFireSensorCo()))
                .build();
    }
}
