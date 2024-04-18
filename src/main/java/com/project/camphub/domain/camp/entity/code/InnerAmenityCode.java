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
public class InnerAmenityCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long innerAmntyCdId;
    @Column(length = 30)
    private String innerAmntyCdNm;

    public InnerAmenityCode(String innerAmntyCdNm) {
        this.innerAmntyCdNm = innerAmntyCdNm;
    }

    public static InnerAmenityCode createInnerAmenityCode(String innerAmntyCdNm) {
        return new InnerAmenityCode(innerAmntyCdNm);
    }

    @Override
    public String getCodeNm() {
        return innerAmntyCdNm;
    }
}
