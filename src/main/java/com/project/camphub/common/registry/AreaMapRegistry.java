package com.project.camphub.common.registry;

import com.project.camphub.domain.common.entity.area.DistrictCode;
import com.project.camphub.domain.common.entity.area.ProvinceCode;
import com.project.camphub.repository.common.area.DistrictCodeRepository;
import com.project.camphub.repository.common.area.ProvinceCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class AreaMapRegistry {

    private final ProvinceCodeRepository provinceCodeRepository;
    private final DistrictCodeRepository districtCodeRepository;

    private final Map<Long, ProvinceCode> provCdMap = new HashMap<>();
    private final Map<Long, DistrictCode> distCdMap = new HashMap<>();
    private final Map<String, ProvinceCode> nameToProvCdMap = new HashMap<>();
    private final Map<String, DistrictCode> nameToDistCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("AreaMapRegistry.init() 실행");

        List<ProvinceCode> provCdList = provinceCodeRepository.findAll();
        List<DistrictCode> distCdList = districtCodeRepository.findAllWithProvinceCode();

        setProvCdMap(provCdList);
        setDistCdMap(distCdList);

        log.info("provCdMap.size()={}", provCdMap.size());
        log.info("nameToProvCdMap.size()={}", nameToProvCdMap.size());
        log.info("distCdMap.size()={}", distCdMap.size());
        log.info("nameToDistCdMap.size()={}", nameToDistCdMap.size());
        log.info("AreaMapRegistry.init() 종료");
    }

    private void setProvCdMap(List<ProvinceCode> provCdList) {
        provCdList.forEach(provCd -> {
            provCdMap.put(provCd.getProvCdId(), provCd);
            nameToProvCdMap.put(provCd.getProvCdNm(), provCd);
        });
    }

    private void setDistCdMap(List<DistrictCode> distCdList) {
        distCdList.forEach(distCd -> {
            distCdMap.put(distCd.getDistCdId(), distCd);

            //시군구의 경우 중복 명이 있기에 복합키를 만든다.
            String uniqueKey = distCd.getProvinceCode().getProvCdNm() + ":" + distCd.getDistCdNm();
            nameToDistCdMap.put(uniqueKey, distCd);
        });
    }

    public ProvinceCode findByProvCdNm(String provCdNm) {
        return nameToProvCdMap.get(provCdNm);
    }

    public DistrictCode findByDistCdNm(String provCdNm, String distCdNm) {
        String key = provCdNm + ":" + distCdNm;
        return nameToDistCdMap.get(key);
    }
}
