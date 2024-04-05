package com.project.camphub.repository.common.area;

import com.project.camphub.domain.common.entity.area.DistrictCode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistrictCodeRepository extends JpaRepository<DistrictCode, Long> {
    @Query("select dsitCd from DistrictCode dsitCd join fetch dsitCd.provinceCode")
    List<DistrictCode> findAllWithProvinceCode();
}