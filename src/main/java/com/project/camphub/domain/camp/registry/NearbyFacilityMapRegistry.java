package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.NearbyFacilityCode;
import com.project.camphub.repository.camp.code.NearbyFacilityCodeRepository;
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
public class NearbyFacilityMapRegistry {

    private final NearbyFacilityCodeRepository nearbyFacilityCodeRepository;

    private final Map<Long, NearbyFacilityCode> nrbyFcltCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("NearbyFacilityMapRegistry.init() 실행");

        List<NearbyFacilityCode> nrbyFcltCdList = nearbyFacilityCodeRepository.findAll();

        setNearbyFacilityCodeMaps(nrbyFcltCdList);

        log.info("nrbyFcltCdMap.size()={}", nrbyFcltCdMap.size());
        log.info("NearbyFacilityMapRegistry.init() 종료");
    }

    private void setNearbyFacilityCodeMaps(List<NearbyFacilityCode> nrbyFcltCdList) {
        nrbyFcltCdList.forEach(nrbyFcltCd ->
                nrbyFcltCdMap.put(nrbyFcltCd.getNrbyFcltCdId(), nrbyFcltCd));
    }

    public List<String> getNrbyFcltCdNmList(List<Long> nrbyFcltCdIdList) {
        if (nrbyFcltCdIdList.isEmpty()) {
            return null;
        }

        return nrbyFcltCdIdList.stream()
                .map(nrbyFcltCdId -> nrbyFcltCdMap.get(nrbyFcltCdId).getNrbyFcltCdNm())
                .toList();
    }
}
