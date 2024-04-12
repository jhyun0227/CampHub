package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampIndustry;
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
public class IndustryCode implements Code {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indstCdId;
    @Column(length = 30)
    private String indstCdNm;

    public IndustryCode(String indstCdNm) {
        this.indstCdNm = indstCdNm;
    }

    @Override
    public String getCodeNm() {
        return indstCdNm;
    }
}
