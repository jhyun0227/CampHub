package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.SeasonCode;
import com.project.camphub.repository.camp.code.SeasonCodeRepository;
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
public class SeasonMapRegistry {

    private final SeasonCodeRepository seasonCodeRepository;

    private final Map<Long, SeasonCode> seasonCdMap = new HashMap<>();
    private final Map<String, SeasonCode> nameToSeasonCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("SeasonMapRegistry.init() 실행");

        List<SeasonCode> seasonCdList = seasonCodeRepository.findAll();

        setSeasonCodeMaps(seasonCdList);

        log.info("seasonCdMap.size()={}", seasonCdMap.size());
        log.info("nameToSeasonCdMap.size()={}", nameToSeasonCdMap.size());
        log.info("SeasonMapRegistry.init() 종료");
    }

    private void setSeasonCodeMaps(List<SeasonCode> seasonCdList) {
        seasonCdList.forEach(seasonCd -> {
            seasonCdMap.put(seasonCd.getSeasonCdId(), seasonCd);
            nameToSeasonCdMap.put(seasonCd.getSeasonCdNm(), seasonCd);
        });
    }

    public SeasonCode findBySeasonCdId(Long seasonCdId) {
        return seasonCdMap.get(seasonCdId);
    }

    public SeasonCode findBySeasonCdNm(String seasonCdNm) {
        return nameToSeasonCdMap.get(seasonCdNm);
    }
}
