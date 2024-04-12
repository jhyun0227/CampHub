package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampNearbyFacility;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NearbyFacilityCode implements Code {

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
