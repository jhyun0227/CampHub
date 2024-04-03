package com.project.camphub.domain.camp.entity.associations.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CampTravelSeasonId implements Serializable {

    @Column(length = 10)
    private String cpId;
    private Long seasonCdId;
}
