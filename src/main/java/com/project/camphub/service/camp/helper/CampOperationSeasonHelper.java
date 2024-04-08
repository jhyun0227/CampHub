package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampOperationSeason;
import com.project.camphub.domain.camp.entity.code.SeasonCode;
import com.project.camphub.domain.camp.registry.SeasonMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
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
public class CampOperationSeasonHelper implements CampCodeHelper<CampOperationSeason, SeasonCode> {

    private final SeasonMapRegistry seasonMapRegistry;
    private final SeasonCodeRepository seasonCodeRepository;

    @Override
    public List<CampOperationSeason> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getOperPdCl());
        if (values == null) {
            return null;
        }

        List<CampOperationSeason> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<SeasonCode> seasonCode = Optional.ofNullable(seasonMapRegistry.findBySeasonCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (seasonCode.isEmpty()) {
                SeasonCode saveSeasonCode = new SeasonCode(value);
                saveCode(saveSeasonCode);
                addCodeToMap(saveSeasonCode);

                resultList.add(new CampOperationSeason(camp, saveSeasonCode));
            } else {
                resultList.add(new CampOperationSeason(camp, seasonCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(SeasonCode code) {
        seasonCodeRepository.save(code);
        log.info("CampOperationSeasonHelper.saveCampCode 실행, id={}, name={}", code.getSeasonCdId(), code.getSeasonCdNm());
    }

    @Override
    public void addCodeToMap(SeasonCode code) {
        seasonMapRegistry.addSeasonCodeMaps(code);
    }
}
