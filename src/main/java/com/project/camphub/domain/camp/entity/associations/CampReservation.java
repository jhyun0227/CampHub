package com.project.camphub.domain.camp.entity.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.ReservationCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampReservation implements Persistable<CampReservation.CampReservationId>, CampAssociation {

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

    @Transient
    private boolean isNew = false;

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CampReservationId implements Serializable {
        @Column(length = 10)
        private String cpId;
        private Long resvCdId;
    }

    public static CampReservation createCampReservation(Camp camp, ReservationCode reservationCode) {
        CampReservationId id = new CampReservationId(camp.getCpId(), reservationCode.getResvCdId());
        return new CampReservation(id, camp, reservationCode, true);
    }

    @Override
    public CampReservationId getId() {
        return getCampReservationId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public String getCampCodeNm() {
        return reservationCode.getResvCdNm();
    }
}
