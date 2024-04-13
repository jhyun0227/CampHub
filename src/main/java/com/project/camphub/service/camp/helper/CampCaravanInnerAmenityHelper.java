package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampCaravanInnerAmenity;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampCaravanInnerAmenityRepository;
import com.project.camphub.repository.camp.code.InnerAmenityCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.INNER_AMENITY_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampCaravanInnerAmenityHelper implements CampAssociationHelper<CampCaravanInnerAmenity, InnerAmenityCode> {

    private final InnerAmenityCodeRepository innerAmenityCodeRepository;
    private final CampCaravanInnerAmenityRepository campCaravanInnerAmenityRepository;

    @Override
    public List<CampCaravanInnerAmenity> getCampAssociationEntity(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getCaravInnerFclty());
        if (values == null) {
            return null;
        }

        List<CampCaravanInnerAmenity> resultList = new ArrayList<>();

        Map<String, InnerAmenityCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<InnerAmenityCode> innerAmenityCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (innerAmenityCode.isEmpty()) {
                InnerAmenityCode saveInnerAmenityCode = new InnerAmenityCode(value);
                saveCode(saveInnerAmenityCode);
                addCodeToMap(saveInnerAmenityCode, nameToCodeMaps);

                resultList.add(createCampAssociation(camp, saveInnerAmenityCode));
            } else {
                resultList.add(createCampAssociation(camp, innerAmenityCode.get()));
            }
        }

        return resultList;
    }

    @Override
    public Map<String, InnerAmenityCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, InnerAmenityCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(INNER_AMENITY_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (InnerAmenityCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(InnerAmenityCode code) {
        innerAmenityCodeRepository.save(code);
        log.info("CampCaravanInnerAmenityHelper.saveCode 실행, id={}, name={}", code.getInnerAmntyCdId(), code.getInnerAmntyCdNm());
    }

    @Override
    public void addCodeToMap(InnerAmenityCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        nameToCodeMaps.get(INNER_AMENITY_CODE).put(code.getInnerAmntyCdNm(), code);
    }

    @Override
    public void saveCampAssociation(List<CampCaravanInnerAmenity> CampAssociationList) {
        campCaravanInnerAmenityRepository.saveAll(CampAssociationList);
    }

    @Override
    public CampCaravanInnerAmenity createCampAssociation(Camp camp, InnerAmenityCode code) {
        return CampCaravanInnerAmenity.createCampCaravanInnerAmenity(camp, code);
    }
}
