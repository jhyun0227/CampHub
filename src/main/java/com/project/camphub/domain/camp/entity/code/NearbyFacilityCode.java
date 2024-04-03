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
public class NearbyFacilityCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nrbyFcltCdId;
    @Column(length = 30)
    private String nrbyFcltCdNm;

    @OneToMany(mappedBy = "nearbyFacilityCode")
    private List<CampNearbyFacility> campNearbyFacilityList = new ArrayList<>();
}
