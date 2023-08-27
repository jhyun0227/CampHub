package com.project.camphub.camp.entity;

import com.project.camphub.externalapi.dto.openapi.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(exclude = "camp")
public class CampSite {

    @Id
    private String cpId;

    @MapsId //해당 엔터티의 주키를 현재 엔터티의 주키로 설정한다. 노션 정리2
    @JoinColumn(name = "cp_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Camp camp;

    private String cpsSitedStnc;
    private String cpsSiteMg1Co;
    private String cpsSiteMg2Co;
    private String cpsSiteMg3Co;
    private String cpsSiteMg1Width;
    private String cpsSiteMg1Vrticl;
    private String cpsSiteMg2Width;
    private String cpsSiteMg2Vrticl;
    private String cpsSiteMg3Width;
    private String cpsSiteMg3Vrticl;
    private String cpsSiteBottomCl1;
    private String cpsSiteBottomCl2;
    private String cpsSiteBottomCl3;
    private String cpsSiteBottomCl4;
    private String cpsSiteBottomCl5;

    /**
     * 데이터 저장
     * OpenApiResponse -> CampSite
     */
    public static CampSite fromOpenApiResponse(Camp camp, Item item) {
        return CampSite.builder()
                .camp(camp)
                .cpsSitedStnc(item.getSitedStnc())
                .cpsSiteMg1Co(item.getSiteMg1Co())
                .cpsSiteMg2Co(item.getSiteMg2Co())
                .cpsSiteMg3Co(item.getSiteMg3Co())
                .cpsSiteMg1Width(item.getSiteMg1Width())
                .cpsSiteMg1Vrticl(item.getSiteMg1Vrticl())
                .cpsSiteMg2Width(item.getSiteMg2Width())
                .cpsSiteMg2Vrticl(item.getSiteMg2Vrticl())
                .cpsSiteMg3Width(item.getSiteMg3Width())
                .cpsSiteMg3Vrticl(item.getSiteMg3Vrticl())
                .cpsSiteBottomCl1(item.getSiteBottomCl1())
                .cpsSiteBottomCl2(item.getSiteBottomCl2())
                .cpsSiteBottomCl3(item.getSiteBottomCl3())
                .cpsSiteBottomCl4(item.getSiteBottomCl4())
                .cpsSiteBottomCl5(item.getSiteBottomCl5())
                .build();
    }

    /**
     * 데이터 수정
     * OpenApiResponse -> Camp
     */
    public void fromSyncOpenApiResponse(Camp camp, Item item) {
        this.camp = camp;
        this.cpsSitedStnc = item.getSitedStnc();
        this.cpsSiteMg1Co = item.getSiteMg1Co();
        this.cpsSiteMg2Co = item.getSiteMg2Co();
        this.cpsSiteMg3Co = item.getSiteMg3Co();
        this.cpsSiteMg1Width = item.getSiteMg1Width();
        this.cpsSiteMg1Vrticl = item.getSiteMg1Vrticl();
        this.cpsSiteMg2Width = item.getSiteMg2Width();
        this.cpsSiteMg2Vrticl = item.getSiteMg2Vrticl();
        this.cpsSiteMg3Width = item.getSiteMg3Width();
        this.cpsSiteMg3Vrticl = item.getSiteMg3Vrticl();
        this.cpsSiteBottomCl1 = item.getSiteBottomCl1();
        this.cpsSiteBottomCl2 = item.getSiteBottomCl2();
        this.cpsSiteBottomCl3 = item.getSiteBottomCl3();
        this.cpsSiteBottomCl4 = item.getSiteBottomCl4();
        this.cpsSiteBottomCl5 = item.getSiteBottomCl5();
    }
}
