package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampCaravanInnerAmenity;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import com.project.camphub.domain.camp.registry.InnerAmenityMapRegistry;
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

    private final CampCaravanInnerAmenityRepository campCaravanInnerAmenityRepository;
    private final InnerAmenityCodeRepository innerAmenityCodeRepository;
    private final InnerAmenityMapRegistry innerAmenityMapRegistry;

    @Override
    public void insertCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getCaravInnerFclty());
        if (values == null) {
            return;
        }

        List<CampCaravanInnerAmenity> saveCampCaravanInnerAmenityList = new ArrayList<>();

        Map<String, InnerAmenityCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<InnerAmenityCode> innerAmenityCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (innerAmenityCode.isEmpty()) {
                InnerAmenityCode saveInnerAmenityCode = InnerAmenityCode.createInnerAmenityCode(value);
                saveCode(saveInnerAmenityCode);
                addCodeToMap(saveInnerAmenityCode, nameToCodeMaps);

                saveCampCaravanInnerAmenityList.add(CampCaravanInnerAmenity.createCampCaravanInnerAmenity(camp, saveInnerAmenityCode));
            } else {
                saveCampCaravanInnerAmenityList.add(CampCaravanInnerAmenity.createCampCaravanInnerAmenity(camp, innerAmenityCode.get()));
            }
        }

        campCaravanInnerAmenityRepository.saveAll(saveCampCaravanInnerAmenityList);
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
        //Registry
        innerAmenityMapRegistry.getInnerAmntyCdMap().put(code.getInnerAmntyCdId(), code);

        //지역변수에서 사용하는 CodeMaps
        nameToCodeMaps.get(INNER_AMENITY_CODE).put(code.getInnerAmntyCdNm(), code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        List<CampCaravanInnerAmenity> findCampCaravanInnerAmenity =
                campCaravanInnerAmenityRepository.findByCampCaravanInnerAmenityId_CpId(camp.getCpId());

        if (!checkUpdate(item, findCampCaravanInnerAmenity)) {
            return;
        }

        //초기화
        campCaravanInnerAmenityRepository.deleteAll(findCampCaravanInnerAmenity);

        //재설정
        insertCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, List<CampCaravanInnerAmenity> campCaravanInnerAmenityList) {
        String[] values = convertStringToArray(item.getCaravInnerFclty());

        /**
         * 넘어온 데이터가 null일 경우 -> false 반환
         * 기존의 데이터가 있으면 업데이트 해당O, 그렇지만 데이터만 없애면 되기에 여기에서 초기화 처리
         * 기존으 데이터가 없을 경우 업데이트 해당X
         */
        if (values == null) {
            if (!campCaravanInnerAmenityList.isEmpty()) {
                //기존에 데이터가 있었다면 초기화
                campCaravanInnerAmenityRepository.deleteAll(campCaravanInnerAmenityList);
            }

            return false;
        }

        //넘어온 데이터의 개수와 기존 등록된 데이터의 수가 다르면 업데이트 해당O
        if (values.length != campCaravanInnerAmenityList.size()) {
            return true;
        }

        List<String> innerAmntyCdNmList = campCaravanInnerAmenityList.stream()
                .map(campCaravanInnerAmenity -> campCaravanInnerAmenity.getInnerAmenityCode().getInnerAmntyCdNm())
                .toList();

        //새로 전달된 값과 비교, 기존 DB에 없으면 업데이트 사항O
        for (String value : values) {
            if (!innerAmntyCdNmList.contains(value)) {
                return true;
            }
        }

        //기존과 일치한다면 업데이트 해당X
        return false;
    }
}
