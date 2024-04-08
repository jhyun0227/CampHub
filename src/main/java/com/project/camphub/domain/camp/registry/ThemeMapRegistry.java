package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.ThemeCode;
import com.project.camphub.repository.camp.code.ThemeCodeRepository;
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
public class ThemeMapRegistry {
    
    private final ThemeCodeRepository themeCodeRepository;
    
    private final Map<Long, ThemeCode> themeCdMap = new HashMap<>();
    private final Map<String, ThemeCode> nameToThemeCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("ThemeMapRegistry.init() 실행");

        List<ThemeCode> themeCdList = themeCodeRepository.findAll();

        setThemeCodeMaps(themeCdList);

        log.info("themeCdMap.size()={}", themeCdMap.size());
        log.info("nameToThemeCdMap.size()={}", nameToThemeCdMap.size());
        log.info("ThemeMapRegistry.init() 종료");
    }

    private void setThemeCodeMaps(List<ThemeCode> themeCdList) {
        themeCdList.forEach(themeCd -> {
            themeCdMap.put(themeCd.getThemeCdId(), themeCd);
            nameToThemeCdMap.put(themeCd.getThemeCdNm(), themeCd);
        });
    }

    public ThemeCode findByThemeCdId(Long themeCdId) {
        return themeCdMap.get(themeCdId);
    }

    public ThemeCode findByThemeCdNm(String themeCdNm) {
        return nameToThemeCdMap.get(themeCdNm);
    }

    public void addThemeCodeMaps(ThemeCode themeCode) {
        themeCdMap.put(themeCode.getThemeCdId(), themeCode);
        nameToThemeCdMap.put(themeCode.getThemeCdNm(), themeCode);
    }
}
