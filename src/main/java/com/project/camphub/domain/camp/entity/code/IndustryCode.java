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
public class IndustryCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indstCdId;
    @Column(length = 30)
    private String indstCdNm;

    public IndustryCode(String indstCdNm) {
        this.indstCdNm = indstCdNm;
    }

    public static IndustryCode createIndustryCode(String indstCdNm) {
        return new IndustryCode(indstCdNm);
    }

    @Override
    public String getCodeNm() {
        return indstCdNm;
    }
}
