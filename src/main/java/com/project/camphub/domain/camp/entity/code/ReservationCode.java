package com.project.camphub.domain.camp.entity.code;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationCode implements CampCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resvCdId;
    @Column(length = 30)
    private String resvCdNm;

    public ReservationCode(String resvCdNm) {
        this.resvCdNm = resvCdNm;
    }

    @Override
    public String getCodeNm() {
        return resvCdNm;
    }
}
