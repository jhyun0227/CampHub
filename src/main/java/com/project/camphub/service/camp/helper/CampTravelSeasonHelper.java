package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampTravelSeason;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.SeasonCode;
import com.project.camphub.domain.camp.registry.SeasonMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampTravelSeasonRepository;
import com.project.camphub.repository.camp.code.SeasonCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.SEASON_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampTravelSeasonHelper implements CampAssociationHelper<CampTravelSeason, SeasonCode> {

    private final CampTravelSeasonRepository campTravelSeasonRepository;
    private final SeasonCodeRepository seasonCodeRepository;
    private final SeasonMapRegistry seasonMapRegistry;

    @Override
    public void insertCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getTourEraCl());
        if (values == null) {
            return;
        }

        List<CampTravelSeason> saveCampTravelSeasonList = new ArrayList<>();

        Map<String, SeasonCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<SeasonCode> seasonCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (seasonCode.isEmpty()) {
                SeasonCode saveSeasonCode = SeasonCode.createSeasonCode(value);
                saveCode(saveSeasonCode);
                addCodeToMap(saveSeasonCode, nameToCodeMaps);

                saveCampTravelSeasonList.add(CampTravelSeason.createCampTravelSeason(camp, saveSeasonCode));
            } else {
                saveCampTravelSeasonList.add(CampTravelSeason.createCampTravelSeason(camp, seasonCode.get()));
            }
        }

        campTravelSeasonRepository.saveAll(saveCampTravelSeasonList);
    }

    @Override
    public Map<String, SeasonCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, SeasonCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(SEASON_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (SeasonCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(SeasonCode code) {
        seasonCodeRepository.save(code);
        log.info("CampTravelSeasonHelper.saveCode 실행, id={}, name={}", code.getSeasonCdId(), code.getSeasonCdNm());
    }

    @Override
    public void addCodeToMap(SeasonCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        seasonMapRegistry.getSeasonCdMap().put(code.getSeasonCdId(), code);

        nameToCodeMaps.get(SEASON_CODE).put(code.getSeasonCdNm(), code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        List<CampTravelSeason> findCampTravelSeasonList = campTravelSeasonRepository.findByCampTravelSeasonId_CpId(camp.getCpId());

        if (!checkUpdate(item, findCampTravelSeasonList)) {
            return;
        }

        campTravelSeasonRepository.deleteAll(findCampTravelSeasonList);

        insertCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, List<CampTravelSeason> campTravelSeasonList) {
        String[] values = convertStringToArray(item.getTourEraCl());

        if (values == null) {
            if (!campTravelSeasonList.isEmpty()) {
                campTravelSeasonRepository.deleteAll(campTravelSeasonList);
            }

            return false;
        }

        if (values.length != campTravelSeasonList.size()) {
            return true;
        }

        List<String> seasonCdNmList = campTravelSeasonList.stream()
                .map(campTravelSeason -> campTravelSeason.getSeasonCode().getSeasonCdNm())
                .toList();

        for (String value : values) {
            if (!seasonCdNmList.contains(value)) {
                return true;
            }
        }

        return false;
    }
}
