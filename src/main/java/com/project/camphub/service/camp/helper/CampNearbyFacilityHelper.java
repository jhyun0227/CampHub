package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampNearbyFacility;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.NearbyFacilityCode;
import com.project.camphub.domain.camp.registry.NearbyFacilityMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.NearbyFacilityCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.NEARBY_FACILITY_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampNearbyFacilityHelper implements CampAssociationHelper<CampNearbyFacility, NearbyFacilityCode> {

    private final NearbyFacilityCodeRepository nearbyFacilityCodeRepository;
    private final NearbyFacilityMapRegistry nearbyFacilityMapRegistry;

    @Override
    public void linkCampAssociations(OpenApiResponse.Item item, Camp camp,  Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getPosblFcltyCl());
        if (values == null) {
            return;
        }

        Map<String, NearbyFacilityCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<NearbyFacilityCode> nearbyFacilityCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (nearbyFacilityCode.isEmpty()) {
                NearbyFacilityCode saveNearbyFacilityCode = NearbyFacilityCode.createNearbyFacilityCode(value);
                saveCode(saveNearbyFacilityCode);
                addCodeToMap(saveNearbyFacilityCode, nameToCodeMaps);

                createCampAssociationAndLinkToCamp(camp, saveNearbyFacilityCode);
            } else {
                createCampAssociationAndLinkToCamp(camp, nearbyFacilityCode.get());
            }
        }
    }

    @Override
    public Map<String, NearbyFacilityCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, NearbyFacilityCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(NEARBY_FACILITY_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (NearbyFacilityCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(NearbyFacilityCode code) {
        nearbyFacilityCodeRepository.save(code);
        log.info("CampNearbyFacilityHelper.saveCode 실행, id={}, name={}", code.getNrbyFcltCdId(), code.getNrbyFcltCdNm());
    }

    @Override
    public void addCodeToMap(NearbyFacilityCode code,  Map<String, Map<String, CampCode>> nameToCodeMaps) {
        nearbyFacilityMapRegistry.getNrbyFcltCdMap().put(code.getNrbyFcltCdId(), code);

        nameToCodeMaps.get(NEARBY_FACILITY_CODE).put(code.getNrbyFcltCdNm(), code);
    }

    @Override
    public void createCampAssociationAndLinkToCamp(Camp camp, NearbyFacilityCode code) {
        CampNearbyFacility.createCampNearbyFacilityAndLinkToCamp(camp, code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        if (!checkUpdate(item, camp)) {
            return;
        }

        camp.resetCampNearbyFacilityList();

        linkCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, Camp camp) {
        List<CampNearbyFacility> campNearbyFacilityList = camp.getCampNearbyFacilityList();
        String[] values = convertStringToArray(item.getPosblFcltyCl());

        if (values == null) {
            if (campNearbyFacilityList.size() != 0) {
                camp.resetCampNearbyFacilityList();
            }

            return false;
        }

        if (values.length != campNearbyFacilityList.size()) {
            return true;
        }

        List<Long> nrbyFcltCdIdList = campNearbyFacilityList.stream()
                .map(campNearbyFacility -> campNearbyFacility.getCampNearbyFacilityId().getNrbyFcltCdId())
                .toList();

        List<String> nrbyFcltCdNmList = nearbyFacilityMapRegistry.getNrbyFcltCdNmListByIds(nrbyFcltCdIdList);

        for (String value : values) {
            if (!nrbyFcltCdNmList.contains(value)) {
                return true;
            }
        }

        return false;
    }
}
