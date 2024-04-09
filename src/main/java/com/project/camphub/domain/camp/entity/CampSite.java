package com.project.camphub.domain.camp.entity;

import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@ToString
public class CampSite implements Persistable<String> {

    @Id
    @Column(length = 10)
    private String cpId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    private Integer cpsSiteDist;

    @Column(name = "cps_site_size1_cnt")
    private Integer cpsSiteSize1Cnt;
    @Column(name = "cps_site_size1_width")
    private Integer cpsSiteSize1Width;
    @Column(name = "cps_site_size1_length")
    private Integer cpsSiteSize1Length;
    @Column(name = "cps_site_size2_cnt")
    private Integer cpsSiteSize2Cnt;
    @Column(name = "cps_site_size2_width")
    private Integer cpsSiteSize2Width;
    @Column(name = "cps_site_size2_length")
    private Integer cpsSiteSize2Length;
    @Column(name = "cps_site_size3_cnt")
    private Integer cpsSiteSize3Cnt;
    @Column(name = "cps_site_size3_width")
    private Integer cpsSiteSize3Width;
    @Column(name = "cps_site_size3_length")
    private Integer cpsSiteSize3Length;

    private Integer cpsBttmGrassCnt;
    private Integer cpsBttmStnCnt;
    private Integer cpsBttmTechCnt;
    private Integer cpsBttmGravelCnt;
    private Integer cpsBttmDirtCnt;

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

    public static CampSite apiToEntity(OpenApiResponse.Item item, Camp camp) {
        return CampSite.builder()
                .camp(camp)
                .cpsSiteDist(Integer.parseInt(item.getSitedStnc()))
                .cpsSiteSize1Cnt(Integer.parseInt(item.getSiteMg1Co()))
                .cpsSiteSize1Width(Integer.parseInt(item.getSiteMg1Width()))
                .cpsSiteSize1Length(Integer.parseInt(item.getSiteMg1Vrticl()))
                .cpsSiteSize2Cnt(Integer.parseInt(item.getSiteMg2Co()))
                .cpsSiteSize2Width(Integer.parseInt(item.getSiteMg2Width()))
                .cpsSiteSize2Length(Integer.parseInt(item.getSiteMg2Vrticl()))
                .cpsSiteSize3Cnt(Integer.parseInt(item.getSiteMg3Co()))
                .cpsSiteSize3Width(Integer.parseInt(item.getSiteMg3Width()))
                .cpsSiteSize3Length(Integer.parseInt(item.getSiteMg3Vrticl()))
                .cpsBttmGrassCnt(Integer.parseInt(item.getSiteBottomCl1()))
                .cpsBttmStnCnt(Integer.parseInt(item.getSiteBottomCl2()))
                .cpsBttmTechCnt(Integer.parseInt(item.getSiteBottomCl3()))
                .cpsBttmGravelCnt(Integer.parseInt(item.getSiteBottomCl4()))
                .cpsBttmDirtCnt(Integer.parseInt(item.getSiteBottomCl5()))
                .build();
    }
}
