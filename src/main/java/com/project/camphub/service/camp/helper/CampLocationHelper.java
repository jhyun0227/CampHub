package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampLocation;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.LocationCode;
import com.project.camphub.domain.camp.registry.LocationMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampLocationRepository;
import com.project.camphub.repository.camp.code.LocationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.LOCATION_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampLocationHelper implements CampAssociationHelper<CampLocation, LocationCode> {

    private final CampLocationRepository campLocationRepository;
    private final LocationCodeRepository locationCodeRepository;
    private final LocationMapRegistry locationMapRegistry;

    @Override
    public void insertCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getLctCl());
        if (values == null) {
            return;
        }

        List<CampLocation> saveCampLocationList = new ArrayList<>();

        Map<String, LocationCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<LocationCode> locationCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (locationCode.isEmpty()) {
                LocationCode saveLocationCode = LocationCode.createLocationCode(value);
                saveCode(saveLocationCode);
                addCodeToMap(saveLocationCode, nameToCodeMaps);

                saveCampLocationList.add(CampLocation.createCampLocation(camp, saveLocationCode));
            } else {
                saveCampLocationList.add(CampLocation.createCampLocation(camp, locationCode.get()));
            }
        }

        campLocationRepository.saveAll(saveCampLocationList);
    }

    @Override
    public Map<String, LocationCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, LocationCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(LOCATION_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (LocationCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(LocationCode code) {
        locationCodeRepository.save(code);
        log.info("CampLocationHelper.saveCode 실행, id={}, name={}", code.getLoctCdId(), code.getLoctCdNm());
    }

    @Override
    public void addCodeToMap(LocationCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        locationMapRegistry.getLoctCdMap().put(code.getLoctCdId(), code);

        nameToCodeMaps.get(LOCATION_CODE).put(code.getLoctCdNm(), code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        List<CampLocation> findCampLocationList = campLocationRepository.findByCampLocationId_CpId(camp.getCpId());

        if (!checkUpdate(item, findCampLocationList)) {
            return;
        }

        campLocationRepository.deleteAll(findCampLocationList);

        insertCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, List<CampLocation> campLocationList) {
        String[] values = convertStringToArray(item.getLctCl());

        if (values == null) {
            if (campLocationList.size() != 0) {
                campLocationRepository.deleteAll(campLocationList);
            }

            return false;
        }

        if (values.length != campLocationList.size()) {
            return true;
        }

        List<String> loctCdNmList = campLocationList.stream()
                .map(campLocation -> campLocation.getLocationCode().getLoctCdNm())
                .toList();

        for (String value : values) {
            if (!loctCdNmList.contains(value)) {
                return true;
            }
        }

        return false;
    }
}
