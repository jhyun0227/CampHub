package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampLocation;
import com.project.camphub.domain.camp.entity.associations.id.CampLocationId;
import com.project.camphub.domain.camp.entity.code.LocationCode;
import com.project.camphub.domain.camp.registry.LocationMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampLocationRepository;
import com.project.camphub.repository.camp.code.LocationCodeRepository;
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
public class CampLocationHelper implements CampCodeHelper<CampLocation, LocationCode> {

    private final LocationMapRegistry locationMapRegistry;
    private final LocationCodeRepository locationCodeRepository;
    private final CampLocationRepository campLocationRepository;

    @Override
    public List<CampLocation> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getLctCl());
        if (values == null) {
            return null;
        }

        List<CampLocation> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<LocationCode> locationCode = Optional.ofNullable(locationMapRegistry.findByLoctCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (locationCode.isEmpty()) {
                LocationCode saveLocationCode = new LocationCode(value);
                saveCode(saveLocationCode);
                addCodeToMap(saveLocationCode);

                resultList.add(createCampCode(camp, saveLocationCode));
            } else {
                resultList.add(createCampCode(camp, locationCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(LocationCode code) {
        locationCodeRepository.save(code);
        log.info("CampLocationHelper.saveCampCode 실행, id={}, name={}", code.getLoctCdId(), code.getLoctCdNm());
    }

    @Override
    public void addCodeToMap(LocationCode code) {
        locationMapRegistry.addLocationCodeMaps(code);
    }

    @Override
    public void saveCampCode(List<CampLocation> campCodeList) {
        campLocationRepository.saveAll(campCodeList);
    }

    @Override
    public CampLocation createCampCode(Camp camp, LocationCode code) {
        CampLocationId id = new CampLocationId(camp.getCpId(), code.getLoctCdId());
        return new CampLocation(id, camp, code);
    }
}
