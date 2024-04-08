package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.LocationCode;
import com.project.camphub.repository.camp.code.LocationCodeRepository;
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
public class LocationMapRegistry {

    private final LocationCodeRepository locationCodeRepository;

    private final Map<Long, LocationCode> loctCdMap = new HashMap<>();
    private final Map<String, LocationCode> nameToLoctCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("LocationMapRegistry.init() 실행");

        List<LocationCode> loctCdList = locationCodeRepository.findAll();

        setLocationCodeMaps(loctCdList);

        log.info("loctCdMap.size()={}", loctCdMap.size());
        log.info("nameToLoctCdMap.size()={}", nameToLoctCdMap.size());
        log.info("LocationMapRegistry.init() 종료");
    }

    private void setLocationCodeMaps (List<LocationCode> loctCdList) {
        loctCdList.forEach(loctCd -> {
            loctCdMap.put(loctCd.getLoctCdId(), loctCd);
            nameToLoctCdMap.put(loctCd.getLoctCdNm(), loctCd);
        });
    }

    public LocationCode findByLoctCdId(Long loctCdId) {
        return loctCdMap.get(loctCdId);
    }

    public LocationCode findByLoctCdNm(String loctCdNm) {
        return nameToLoctCdMap.get(loctCdNm);
    }

    public void addLocationCodeMaps(LocationCode locationCode) {
        loctCdMap.put(locationCode.getLoctCdId(), locationCode);
        nameToLoctCdMap.put(locationCode.getLoctCdNm(), locationCode);
    }
}
