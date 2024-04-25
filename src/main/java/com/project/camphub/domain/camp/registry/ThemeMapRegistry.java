package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.ThemeCode;
import com.project.camphub.repository.camp.code.ThemeCodeRepository;
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
public class ThemeMapRegistry {
    
    private final ThemeCodeRepository themeCodeRepository;
    
    private final Map<Long, ThemeCode> themeCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("ThemeMapRegistry.init() 실행");

        List<ThemeCode> themeCdList = themeCodeRepository.findAll();

        setThemeCodeMaps(themeCdList);

        log.info("themeCdMap.size()={}", themeCdMap.size());
        log.info("ThemeMapRegistry.init() 종료");
    }

    private void setThemeCodeMaps(List<ThemeCode> themeCdList) {
        themeCdList.forEach(themeCd ->
                themeCdMap.put(themeCd.getThemeCdId(), themeCd));
    }

    public List<String> getThemeCdNmList(List<Long> themeCdIdList) {
        if (themeCdIdList.isEmpty()) {
            return null;
        }

        return themeCdIdList.stream()
                .map(themeCdId -> themeCdMap.get(themeCdId).getThemeCdNm())
                .toList();
    }
}
