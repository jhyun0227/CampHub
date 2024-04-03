package com.project.camphub.domain.common.entity.area;

import com.project.camphub.domain.camp.entity.Camp;
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
public class ProvinceCode {

    @Id
    @Column(length = 2)
    private String provCdId;
    @Column(length = 10)
    private String provCdNm;

    @OneToMany(mappedBy = "provinceCode")
    private List<DistrictCode> districtCodeList = new ArrayList<>();

    @OneToMany(mappedBy = "provinceCode")
    private List<Camp> campList = new ArrayList<>();
}
