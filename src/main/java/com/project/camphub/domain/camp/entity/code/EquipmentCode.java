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
public class EquipmentCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipCdId;
    @Column(length = 30)
    private String equipCdNm;

    public EquipmentCode(String equipCdNm) {
        this.equipCdNm = equipCdNm;
    }

    @Override
    public String getCodeNm() {
        return equipCdNm;
    }
}
