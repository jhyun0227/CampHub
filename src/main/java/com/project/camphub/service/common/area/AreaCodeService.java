package com.project.camphub.service.common.area;

import com.project.camphub.domain.common.entity.area.DistrictCode;
import com.project.camphub.domain.common.entity.area.ProvinceCode;
import com.project.camphub.repository.common.area.DistrictCodeRepository;
import com.project.camphub.repository.common.area.ProvinceCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AreaCodeService {

    private final ProvinceCodeRepository provinceCodeRepository;
    private final DistrictCodeRepository districtCodeRepository;

    public Map<String, ProvinceCode> getNameToProvinceCodeMap() {
        Map<String, ProvinceCode> resultList = new HashMap<>();

        List<ProvinceCode> provinceCodeList = provinceCodeRepository.findAll();

        provinceCodeList.forEach(provinceCode -> {
            resultList.put(provinceCode.getProvCdNm(), provinceCode);
        });

        log.info("nameToProvinceCodeMap.size() = {}", resultList.size());

        return resultList;
    }

    public Map<String, DistrictCode> getNameToDistrictCodeMap() {
        Map<String, DistrictCode> resultList = new HashMap<>();

        List<DistrictCode> districtCodeList = districtCodeRepository.findAllWithProvinceCode();

        districtCodeList.forEach(districtCode -> {
            String key = districtCode.getProvinceCode().getProvCdNm() + ":" + districtCode.getDistCdNm();
            resultList.put(key, districtCode);
        });

        log.info("nameToDistrictCodeMap.size() = {}", resultList.size());

        return resultList;
    }
}
