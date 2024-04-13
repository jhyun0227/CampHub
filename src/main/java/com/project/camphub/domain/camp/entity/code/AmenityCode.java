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
public class AmenityCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amntyCdId;
    @Column(length = 30)
    private String amntyCdNm;

    public AmenityCode(String amntyCdNm) {
        this.amntyCdNm = amntyCdNm;
    }

    @Override
    public String getCodeNm() {
        return amntyCdNm;
    }
}
