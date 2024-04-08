package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampEquipmentRental;
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
public class EquipmentCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipCdId;
    @Column(length = 30)
    private String equipCdNm;

    public EquipmentCode(String equipCdNm) {
        this.equipCdNm = equipCdNm;
    }
}
