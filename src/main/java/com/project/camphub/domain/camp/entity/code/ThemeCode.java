package com.project.camphub.domain.camp.entity.code;

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
public class ThemeCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeCdId;
    private String themeCdNm;

    @OneToMany(mappedBy = "themeCode")
    private List<ThemeCode> themeCodeList = new ArrayList<>();
}
