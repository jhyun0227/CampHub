package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampNearbyFacility;
import com.project.camphub.domain.camp.entity.code.NearbyFacilityCode;
import com.project.camphub.domain.camp.registry.NearbyFacilityMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.NearbyFacilityCodeRepository;
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
public class CampNearbyFacilityHelper implements CampCodeHelper<CampNearbyFacility, NearbyFacilityCode> {

    private final NearbyFacilityMapRegistry nearbyFacilityMapRegistry;
    private final NearbyFacilityCodeRepository nearbyFacilityCodeRepository;

    @Override
    public List<CampNearbyFacility> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getPosblFcltyCl());
        if (values == null) {
            return null;
        }

        List<CampNearbyFacility> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<NearbyFacilityCode> nearbyFacilityCode = Optional.ofNullable(nearbyFacilityMapRegistry.findByNrbyFcltCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (nearbyFacilityCode.isEmpty()) {
                NearbyFacilityCode saveNearbyFacilityCode = new NearbyFacilityCode(value);
                saveCode(saveNearbyFacilityCode);
                addCodeToMap(saveNearbyFacilityCode);

                resultList.add(new CampNearbyFacility(camp, saveNearbyFacilityCode));
            } else {
                resultList.add(new CampNearbyFacility(camp, nearbyFacilityCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(NearbyFacilityCode code) {
        nearbyFacilityCodeRepository.save(code);
        log.info("CampNearbyFacilityHelper.saveCampCode 실행, id={}, name={}", code.getNrbyFcltCdId(), code.getNrbyFcltCdNm());
    }

    @Override
    public void addCodeToMap(NearbyFacilityCode code) {
        nearbyFacilityMapRegistry.addNearbyFacilityCodeMaps(code);
    }
}
