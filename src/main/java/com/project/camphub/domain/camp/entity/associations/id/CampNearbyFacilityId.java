package com.project.camphub.domain.camp.entity.associations.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CampNearbyFacilityId implements Serializable {

    @Column(length = 10)
    private String cpId;
    private Long nrbyFcltCdId;
}
