package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampAmenity;
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
public class AmenityCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amntyCdId;
    @Column(length = 30)
    private String amntyCdNm;

    public AmenityCode(String amntyCdNm) {
        this.amntyCdNm = amntyCdNm;
    }
}
