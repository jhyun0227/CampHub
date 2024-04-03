package com.project.camphub.domain.camp.entity.code;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resvCdId;
    private String resvCdNm;
}
