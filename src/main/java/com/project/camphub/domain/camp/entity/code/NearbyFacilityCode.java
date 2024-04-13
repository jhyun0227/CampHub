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
public class NearbyFacilityCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nrbyFcltCdId;
    @Column(length = 30)
    private String nrbyFcltCdNm;

    public NearbyFacilityCode(String nrbyFcltCdNm) {
        this.nrbyFcltCdNm = nrbyFcltCdNm;
    }

    @Override
    public String getCodeNm() {
        return nrbyFcltCdNm;
    }
}
