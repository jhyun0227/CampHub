package com.project.camphub.domain.camp.entity.code;

import com.project.camphub.domain.camp.entity.associations.CampLocation;
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
public class LocationCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loctCdId;
    @Column(length = 30)
    private String loctCdNm;

    @OneToMany(mappedBy = "locationCode")
    private List<CampLocation> campLocationList = new ArrayList<>();
}
