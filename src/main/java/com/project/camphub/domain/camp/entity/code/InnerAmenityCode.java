package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampCaravanInnerAmenity;
import com.project.camphub.domain.camp.entity.associations.CampGlampingInnerAmenity;
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
public class InnerAmenityCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long innerAmntyCdId;
    @Column(length = 30)
    private String innerAmntyCdNm;

    @OneToMany(mappedBy = "innerAmenityCode")
    private List<CampCaravanInnerAmenity> campCaravanInnerAmenityList = new ArrayList<>();

    @OneToMany(mappedBy = "innerAmenityCode")
    private List<CampGlampingInnerAmenity> campGlampingInnerAmenityList = new ArrayList<>();
}
