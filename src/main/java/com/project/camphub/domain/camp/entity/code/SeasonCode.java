package com.project.camphub.domain.camp.entity.code;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SeasonCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonCdId;
    @Column(length = 30)
    private String seasonCdNm;

    public SeasonCode(String seasonCdNm) {
        this.seasonCdNm = seasonCdNm;
    }

    public static SeasonCode createSeasonCode(String seasonCdNm) {
        return new SeasonCode(seasonCdNm);
    }

    @Override
    public String getCodeNm() {
        return seasonCdNm;
    }
}
