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
public class LocationCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loctCdId;
    @Column(length = 30)
    private String loctCdNm;

    public LocationCode(String loctCdNm) {
        this.loctCdNm = loctCdNm;
    }

    public static LocationCode createLocationCode(String loctCdNm) {
        return new LocationCode(loctCdNm);
    }

    @Override
    public String getCodeNm() {
        return loctCdNm;
    }
}
