package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampTheme;
import com.project.camphub.domain.camp.entity.code.ThemeCode;
import com.project.camphub.domain.camp.registry.ThemeMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.ThemeCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampThemeHelper implements CampCodeHelper<CampTheme, ThemeCode> {

    private final ThemeMapRegistry themeMapRegistry;
    private final ThemeCodeRepository themeCodeRepository;

    @Override
    public List<CampTheme> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getThemaEnvrnCl());
        if (values == null) {
            return null;
        }

        List<CampTheme> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<ThemeCode> themeCode = Optional.ofNullable(themeMapRegistry.findByThemeCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (themeCode.isEmpty()) {
                ThemeCode saveThemeCode = new ThemeCode(value);
                saveCode(saveThemeCode);
                addCodeToMap(saveThemeCode);

                resultList.add(new CampTheme(camp, saveThemeCode));
            } else {
                resultList.add(new CampTheme(camp, themeCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(ThemeCode code) {
        themeCodeRepository.save(code);
        log.info("CampThemeHelper.saveCampCode 실행, id={}, name={}", code.getThemeCdId(), code.getThemeCdNm());
    }

    @Override
    public void addCodeToMap(ThemeCode code) {
        themeMapRegistry.addThemeCodeMaps(code);
    }
}
