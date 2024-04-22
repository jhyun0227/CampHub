package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampAmenity;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.registry.AmenityMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampAmenityRepository;
import com.project.camphub.repository.camp.code.AmenityCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.project.camphub.domain.camp.CampCodeConst.AMENITY_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampAmenityHelper implements CampAssociationHelper<CampAmenity, AmenityCode> {

    private final CampAmenityRepository campAmenityRepository;
    private final AmenityCodeRepository amenityCodeRepository;
    private final AmenityMapRegistry amenityMapRegistry;

    @Override
    public void insertCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        String[] values = convertStringToArray(item.getSbrsCl());
        if (values == null) {
            return;
        }

        List<CampAmenity> saveCampAmenity = new ArrayList<>();

        Map<String, AmenityCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<AmenityCode> amenityCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Maps에 해당 객체 추가
            if (amenityCode.isEmpty()) {
                AmenityCode saveAmenityCode = AmenityCode.createAmenityCode(value);
                saveCode(saveAmenityCode);
                addCodeToMap(saveAmenityCode, nameToCodeMaps);

                saveCampAmenity.add(createCampAssociation(camp, saveAmenityCode));
            } else {
                saveCampAmenity.add(createCampAssociation(camp, amenityCode.get()));
            }
        }

        campAmenityRepository.saveAll(saveCampAmenity);
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
        //Registry
        amenityMapRegistry.getAmntyCdMap().put(code.getAmntyCdId(), code);

        //지역변수에서 사용하는 CodeMaps
        nameToCodeMaps.get(AMENITY_CODE).put(code.getAmntyCdNm(), code);
    }

    @Override
    public CampAmenity createCampAssociation(Camp camp, AmenityCode code) {
        return CampAmenity.createCampAmenity(camp, code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        List<CampAmenity> findCampAmenityList = campAmenityRepository.findByCampAmenityId_CpId(camp.getCpId());

        if (!checkUpdate(item, findCampAmenityList)) {
            return;
        }

        //초기화
        campAmenityRepository.deleteAll(findCampAmenityList);

        //재설정
        insertCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, List<CampAmenity> campAmenityList) {
        String[] values = convertStringToArray(item.getSbrsCl());

        /**
         * 넘어온 데이터가 null일 경우 -> false 반환
         * 기존의 데이터가 있으면 업데이트 해당O, 그렇지만 데이터만 없애면 되기에 여기에서 초기화 처리
         * 기존으 데이터가 없을 경우 업데이트 해당X
         */
        if (values == null) {
            if (campAmenityList.size() != 0) {
                //기존에 데이터가 있었다면 초기화
                campAmenityRepository.deleteAll(campAmenityList);
            }

            return false;
        }

        //넘어온 데이터의 개수와 기존 등록된 데이터의 수가 다르면 업데이트 해당O
        if (values.length != campAmenityList.size()) {
            return true;
        }

        /**
         * 변경된 항목이 있는지 확인
         */
        List<String> amntyCdNmList = campAmenityList.stream()
                .map(campAmenity -> campAmenity.getAmenityCode().getAmntyCdNm())
                .toList();

        //새로 전달된 값과 비교, 기존 DB에 없으면 업데이트 사항O
        for (String value : values) {
            if (!amntyCdNmList.contains(value)) {
                return true;
            }
        }

        //기존과 일치한다면 업데이트 해당X
        return false;
    }
}
