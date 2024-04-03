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
public class DistrictCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long distCdId;
    @Column(length = 10)
    private String distCdNm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prov_cd_id")
    private ProvinceCode provinceCode;

    @OneToMany(mappedBy = "districtCode")
    private List<Camp> campList = new ArrayList<>();
}
