package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampTravelSeason;
import com.project.camphub.domain.camp.entity.code.SeasonCode;
import com.project.camphub.domain.camp.registry.SeasonMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampTravelSeasonRepository;
import com.project.camphub.repository.camp.code.SeasonCodeRepository;
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
public class CampTravelSeasonHelper implements CampCodeHelper<CampTravelSeason, SeasonCode> {

    private final SeasonMapRegistry seasonMapRegistry;
    private final SeasonCodeRepository seasonCodeRepository;
    private final CampTravelSeasonRepository campTravelSeasonRepository;

    @Override
    public List<CampTravelSeason> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getTourEraCl());
        if (values == null) {
            return null;
        }

        List<CampTravelSeason> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<SeasonCode> seasonCode = Optional.ofNullable(seasonMapRegistry.findBySeasonCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (seasonCode.isEmpty()) {
                SeasonCode saveSeasonCode = new SeasonCode(value);
                saveCode(saveSeasonCode);
                addCodeToMap(saveSeasonCode);

                resultList.add(createCampCode(camp, saveSeasonCode));
            } else {
                resultList.add(createCampCode(camp, seasonCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(SeasonCode code) {
        seasonCodeRepository.save(code);
        log.info("CampTravelSeasonHelper.saveCampCode 실행, id={}, name={}", code.getSeasonCdId(), code.getSeasonCdNm());
    }

    @Override
    public void addCodeToMap(SeasonCode code) {
        seasonMapRegistry.addSeasonCodeMaps(code);
    }

    @Override
    public void saveCampCode(List<CampTravelSeason> campCodeList) {
        campTravelSeasonRepository.saveAll(campCodeList);
    }

    @Override
    public CampTravelSeason createCampCode(Camp camp, SeasonCode code) {
        return CampTravelSeason.createCampTravelSeason(camp, code);
    }
}
