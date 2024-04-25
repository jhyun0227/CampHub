package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.LocationCode;
import com.project.camphub.repository.camp.code.LocationCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    @PostConstruct
    public void init() {
        log.info("LocationMapRegistry.init() 실행");

        List<LocationCode> loctCdList = locationCodeRepository.findAll();

        setLocationCodeMaps(loctCdList);

        log.info("loctCdMap.size()={}", loctCdMap.size());
        log.info("LocationMapRegistry.init() 종료");
    }

    private void setLocationCodeMaps (List<LocationCode> loctCdList) {
        loctCdList.forEach(loctCd ->
                loctCdMap.put(loctCd.getLoctCdId(), loctCd));
    }

    public List<String> getLocdCdNmList(List<Long> loctCdIdList) {
        if (loctCdIdList.isEmpty()) {
            return null;
        }

        return loctCdIdList.stream()
                .map(loctCdId -> loctCdMap.get(loctCdId).getLoctCdNm())
                .toList();
    }
}
