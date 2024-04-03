package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.id.CampReservationId;
import com.project.camphub.domain.camp.entity.code.ReservationCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampReservation {

    @EmbeddedId
    private CampReservationId campReservationId;

    @MapsId(value = "cpId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_id")
    private Camp camp;

    @MapsId(value = "resvCdId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resv_cd_id")
    private ReservationCode reservationCode;
}
