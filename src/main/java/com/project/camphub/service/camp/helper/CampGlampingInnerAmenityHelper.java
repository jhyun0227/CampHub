package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampGlampingInnerAmenity;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import com.project.camphub.domain.camp.registry.InnerAmenityMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampGlampingInnerAmenityRepository;
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
public class CampGlampingInnerAmenityHelper implements CampAssociationHelper<CampGlampingInnerAmenity, InnerAmenityCode> {

    private final CampGlampingInnerAmenityRepository campGlampingInnerAmenityRepository;
    private final InnerAmenityCodeRepository innerAmenityCodeRepository;
    private final InnerAmenityMapRegistry innerAmenityMapRegistry;

    @Override
    public void insertCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getGlampInnerFclty());
        if (values == null) {
            return;
        }

        List<CampGlampingInnerAmenity> saveCampGlampingInnerAmenityList = new ArrayList<>();

        Map<String, InnerAmenityCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<InnerAmenityCode> innerAmenityCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (innerAmenityCode.isEmpty()) {
                InnerAmenityCode saveInnerAmenityCode = InnerAmenityCode.createInnerAmenityCode(value);
                saveCode(saveInnerAmenityCode);
                addCodeToMap(saveInnerAmenityCode, nameToCodeMaps);

                saveCampGlampingInnerAmenityList.add(CampGlampingInnerAmenity.createCampGlampingInnerAmenity(camp, saveInnerAmenityCode));
            } else {
                saveCampGlampingInnerAmenityList.add(CampGlampingInnerAmenity.createCampGlampingInnerAmenity(camp, innerAmenityCode.get()));
            }
        }

        campGlampingInnerAmenityRepository.saveAll(saveCampGlampingInnerAmenityList);
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
        log.info("CampGlampingInnerAmenityHelper.saveCode 실행, id={}, name={}", code.getInnerAmntyCdId(), code.getInnerAmntyCdNm());
    }

    @Override
    public void addCodeToMap(InnerAmenityCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        //Registry
        innerAmenityMapRegistry.getInnerAmntyCdMap().put(code.getInnerAmntyCdId(), code);

        //지역변수에서 사용하는 CodeMaps
        nameToCodeMaps.get(INNER_AMENITY_CODE).put(code.getInnerAmntyCdNm(), code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        List<CampGlampingInnerAmenity> findCampGlampingInnerAmenityList
                = campGlampingInnerAmenityRepository.findByCampGlampingInnerAmenityId_CpId(camp.getCpId());

        if (!checkUpdate(item, findCampGlampingInnerAmenityList)) {
            return;
        }

        campGlampingInnerAmenityRepository.deleteAll(findCampGlampingInnerAmenityList);

        insertCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, List<CampGlampingInnerAmenity> campGlampingInnerAmenityList) {
        String[] values = convertStringToArray(item.getGlampInnerFclty());

        if (values == null) {
            if (!campGlampingInnerAmenityList.isEmpty()) {
                campGlampingInnerAmenityRepository.deleteAll(campGlampingInnerAmenityList);
            }

            return false;
        }

        if (values.length != campGlampingInnerAmenityList.size()) {
            return true;
        }

        List<String> innerAmntyCdNmList = campGlampingInnerAmenityList.stream()
                .map(campGlampingInnerAmenity -> campGlampingInnerAmenity.getInnerAmenityCode().getInnerAmntyCdNm())
                .toList();

        for (String value : values) {
            if (!innerAmntyCdNmList.contains(value)) {
                return true;
            }
        }

        return false;
    }
}
