package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.NearbyFacilityCode;
import com.project.camphub.repository.camp.code.NearbyFacilityCodeRepository;
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
public class NearbyFacilityMapRegistry {

    private final NearbyFacilityCodeRepository nearbyFacilityCodeRepository;

    private final Map<Long, NearbyFacilityCode> nrbyFcltCdMap = new HashMap<>();
    private final Map<String, NearbyFacilityCode> nameToNrbyFcltCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("NearbyFacilityMapRegistry.init() 실행");

        List<NearbyFacilityCode> nrbyFcltCdList = nearbyFacilityCodeRepository.findAll();

        setNearbyFacilityCodeMaps(nrbyFcltCdList);

        log.info("nrbyFcltCdMap.size()={}", nrbyFcltCdMap.size());
        log.info("nameToNrbyFcltCdMap.size()={}", nameToNrbyFcltCdMap.size());
        log.info("NearbyFacilityMapRegistry.init() 종료");
    }

    private void setNearbyFacilityCodeMaps(List<NearbyFacilityCode> nrbyFcltCdList) {
        nrbyFcltCdList.forEach(nrbyFcltCd -> {
            nrbyFcltCdMap.put(nrbyFcltCd.getNrbyFcltCdId(), nrbyFcltCd);
            nameToNrbyFcltCdMap.put(nrbyFcltCd.getNrbyFcltCdNm(), nrbyFcltCd);
        });
    }

    public NearbyFacilityCode findByNrbyFcltCdId(Long nrbyFcltCdId) {
        return nrbyFcltCdMap.get(nrbyFcltCdId);
    }

    public NearbyFacilityCode findByNrbyFcltCdNm(String nrbyFcltCdNm) {
        return nameToNrbyFcltCdMap.get(nrbyFcltCdNm);
    }
}
