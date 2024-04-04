package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampReservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resvCdId;
    @Column(length = 30)
    private String resvCdNm;

}
