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
public class ThemeCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeCdId;
    @Column(length = 30)
    private String themeCdNm;

    public ThemeCode(String themeCdNm) {
        this.themeCdNm = themeCdNm;
    }

    @Override
    public String getCodeNm() {
        return themeCdNm;
    }
}
