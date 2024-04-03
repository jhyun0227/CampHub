package com.project.camphub.domain.camp.entity.code;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SeasonCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonCdId;
    private String seasonCdNm;
}
