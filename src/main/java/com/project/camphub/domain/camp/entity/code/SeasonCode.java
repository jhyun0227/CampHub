package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampOperationSeason;
import com.project.camphub.domain.camp.entity.associations.CampTravelSeason;
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
public class SeasonCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonCdId;
    private String seasonCdNm;

    @OneToMany(mappedBy = "seasonCode")
    private List<CampOperationSeason> campOperationSeasonList = new ArrayList<>();

    @OneToMany(mappedBy = "seasonCode")
    private List<CampTravelSeason> campTravelSeasonList = new ArrayList<>();
}
