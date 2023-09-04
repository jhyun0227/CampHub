package com.project.camphub.camp.dto;

import com.project.camphub.camp.entity.Camp;
import com.project.camphub.camp.entity.CampSite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Builder
public class CampSiteDto {

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
     * Entity -> Dto
     */
    public static CampSiteDto fromEntity(CampSite campSite) {
        return CampSiteDto.builder()
                .cpsSitedStnc(campSite.getCpsSitedStnc())
                .cpsSiteMg1Co(campSite.getCpsSiteMg1Co())
                .cpsSiteMg2Co(campSite.getCpsSiteMg2Co())
                .cpsSiteMg3Co(campSite.getCpsSiteMg3Co())
                .cpsSiteMg1Width(campSite.getCpsSiteMg1Width())
                .cpsSiteMg1Vrticl(campSite.getCpsSiteMg1Vrticl())
                .cpsSiteMg2Width(campSite.getCpsSiteMg2Width())
                .cpsSiteMg2Vrticl(campSite.getCpsSiteMg2Vrticl())
                .cpsSiteMg3Width(campSite.getCpsSiteMg3Width())
                .cpsSiteMg3Vrticl(campSite.getCpsSiteMg3Vrticl())
                .cpsSiteBottomCl1(campSite.getCpsSiteBottomCl1())
                .cpsSiteBottomCl2(campSite.getCpsSiteBottomCl2())
                .cpsSiteBottomCl3(campSite.getCpsSiteBottomCl3())
                .cpsSiteBottomCl4(campSite.getCpsSiteBottomCl4())
                .cpsSiteBottomCl5(campSite.getCpsSiteBottomCl5())
                .build();
    }
}
