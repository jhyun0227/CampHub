package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampAmenity;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampAmenityRepository;
import com.project.camphub.repository.camp.code.AmenityCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.AMENITY_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampAmenityHelper implements CampAssociationHelper<CampAmenity, AmenityCode> {

    private final AmenityCodeRepository amenityCodeRepository;
    private final CampAmenityRepository campAmenityRepository;

    @Override
    public List<CampAmenity> getCampAssociationEntity(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getSbrsCl());
        if (values == null) {
            return null;
        }

        List<CampAmenity> resultList = new ArrayList<>();

        Map<String, AmenityCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<AmenityCode> amenityCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Maps에 해당 객체 추가
            if (amenityCode.isEmpty()) {
                AmenityCode saveAmenityCode = new AmenityCode(value);
                saveCode(saveAmenityCode);
                addCodeToMap(saveAmenityCode, nameToCodeMaps);

                resultList.add(createCampAssociation(camp, saveAmenityCode));
            } else {
                resultList.add(createCampAssociation(camp, amenityCode.get()));
            }
        }

        return resultList;
    }

    @Override
    public Map<String, AmenityCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, AmenityCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(AMENITY_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (AmenityCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(AmenityCode code) {
        amenityCodeRepository.save(code);
        log.info("CampAmenityHelper.saveCode 실행, id={}, name={}", code.getAmntyCdId(), code.getAmntyCdNm());
    }

    @Override
    public void addCodeToMap(AmenityCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        nameToCodeMaps.get(AMENITY_CODE).put(code.getAmntyCdNm(), code);
    }

    @Override
    public void saveCampAssociation(List<CampAmenity> CampAssociationList) {
        campAmenityRepository.saveAll(CampAssociationList);
    }

    @Override
    public CampAmenity createCampAssociation(Camp camp, AmenityCode code) {
        return CampAmenity.createCampAmenity(camp, code);
    }
}
