package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampTheme;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.ThemeCode;
import com.project.camphub.domain.camp.registry.ThemeMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampThemeRepository;
import com.project.camphub.repository.camp.code.ThemeCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.THEME_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampThemeHelper implements CampAssociationHelper<CampTheme, ThemeCode> {

    private final CampThemeRepository campThemeRepository;
    private final ThemeCodeRepository themeCodeRepository;
    private final ThemeMapRegistry themeMapRegistry;

    @Override
    public void insertCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps, String status) {

        String[] values = convertStringToArray(item.getThemaEnvrnCl());
        if (values == null) {
            return;
        }

        List<CampTheme> saveCampThemeList = new ArrayList<>();

        Map<String, ThemeCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<ThemeCode> themeCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (themeCode.isEmpty()) {
                ThemeCode saveThemeCode = ThemeCode.createThemeCode(value);
                saveCode(saveThemeCode);
                addCodeToMap(saveThemeCode, nameToCodeMaps);

                saveCampThemeList.add(CampTheme.createCampTheme(camp, saveThemeCode));
            } else {
                saveCampThemeList.add(CampTheme.createCampTheme(camp, themeCode.get()));
            }
        }

        if (UPDATE.equals(status)) {
            log.info("CampThemeHelper.updateCampAssociations 업데이트 조건 충족. cpId = {}, 수정값 = {}", camp.getCpId(), campAssociationsToString(saveCampThemeList));
        }

        campThemeRepository.saveAll(saveCampThemeList);
    }

    @Override
    public Map<String, ThemeCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, ThemeCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(THEME_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (ThemeCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(ThemeCode code) {
        themeCodeRepository.save(code);
        log.info("CampThemeHelper.saveCode 실행, id={}, name={}", code.getThemeCdId(), code.getThemeCdNm());
    }

    @Override
    public void addCodeToMap(ThemeCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        themeMapRegistry.getThemeCdMap().put(code.getThemeCdId(), code);

        nameToCodeMaps.get(THEME_CODE).put(code.getThemeCdNm(), code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        List<CampTheme> findCampThemeList = campThemeRepository.findByCampThemeId_CpId(camp.getCpId());

        if (!checkUpdate(item, findCampThemeList)) {
            return;
        }

        log.info("CampThemeHelper.updateCampAssociations 업데이트 조건 충족. cpId = {}, 기존값 = {}", camp.getCpId(), campAssociationsToString(findCampThemeList));

        campThemeRepository.deleteAll(findCampThemeList);

        insertCampAssociations(item, camp, nameToCodeMaps, UPDATE);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, List<CampTheme> campThemeList) {
        String[] values = convertStringToArray(item.getThemaEnvrnCl());

        if (values == null) {
            if (!campThemeList.isEmpty()) {
                campThemeRepository.deleteAll(campThemeList);
            }

            return false;
        }

        if (values.length != campThemeList.size()) {
            return true;
        }

        List<String> themeCdNmList = campThemeList.stream()
                .map(campTheme -> campTheme.getThemeCode().getThemeCdNm())
                .toList();

        for (String value : values) {
            if (!themeCdNmList.contains(value)) {
                return true;
            }
        }

        return false;
    }
}
