package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampEquipmentRental;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import com.project.camphub.domain.camp.registry.EquipmentMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.EquipmentCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.EQUIPMENT_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampEquipmentRentalHelper implements CampAssociationHelper<CampEquipmentRental, EquipmentCode> {

    private final EquipmentCodeRepository equipmentCodeRepository;
    private final EquipmentMapRegistry equipmentMapRegistry;

    @Override
    public void linkCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getEqpmnLendCl());
        if (values == null) {
            return;
        }

        Map<String, EquipmentCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<EquipmentCode> equipmentCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (equipmentCode.isEmpty()) {
                EquipmentCode saveEquipmentCode = EquipmentCode.createEquipmentCode(value);
                saveCode(saveEquipmentCode);
                addCodeToMap(saveEquipmentCode, nameToCodeMaps);

                createCampAssociationAndLinkToCamp(camp, saveEquipmentCode);
            } else {
                createCampAssociationAndLinkToCamp(camp, equipmentCode.get());
            }
        }
    }

    @Override
    public Map<String, EquipmentCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, EquipmentCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(EQUIPMENT_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (EquipmentCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(EquipmentCode code) {
        equipmentCodeRepository.save(code);
        log.info("CampEquipmentRentalHelper.saveCode 실행, id={}, name={}", code.getEquipCdId(), code.getEquipCdNm());
    }

    @Override
    public void addCodeToMap(EquipmentCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        //Registry
        equipmentMapRegistry.getEquipCdMap().put(code.getEquipCdId(), code);

        //지역변수에서 사용하는 CodeMaps
        nameToCodeMaps.get(EQUIPMENT_CODE).put(code.getEquipCdNm(), code);
    }

    @Override
    public void createCampAssociationAndLinkToCamp(Camp camp, EquipmentCode code) {
        CampEquipmentRental.createCampEquipmentRentalAndLinkToCamp(camp, code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        if (!checkUpdate(item, camp)) {
            return;
        }

        //초기화
        camp.resetCampEquipmentRentalList();

        //재설정
        linkCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, Camp camp) {
        List<CampEquipmentRental> campEquipmentRentalList = camp.getCampEquipmentRentalList();
        String[] values = convertStringToArray(item.getEqpmnLendCl());

        /**
         * 넘어온 데이터가 null일 경우 -> false 반환
         * 기존의 데이터가 있으면 업데이트 해당O, 그렇지만 데이터만 없애면 되기에 여기에서 초기화 처리
         * 기존으 데이터가 없을 경우 업데이트 해당X
         */
        if (values == null) {
            if (campEquipmentRentalList.size() != 0) {
                //기존에 데이터가 있었다면 초기화
                camp.resetCampEquipmentRentalList();
            }

            return false;
        }

        //넘어온 데이터의 개수와 기존 등록된 데이터의 수가 다르면 업데이트 해당O
        if (values.length != campEquipmentRentalList.size()) {
            return true;
        }

        /**
         * 변경된 항목이 있는지 확인
         * 기존 데이터베이스에 등록된 코드명을 List로 만든 후
         * API를 통해 전달된 값들을 포함하는지 확인한다.
         * 새로 전달된 값들 중 하나라도 기존 데이터베이스로 생성한 List에 포함되지 않는다면 업데이트 대상O
         */
        List<Long> equipCdIdList = campEquipmentRentalList.stream()
                .map(campEquipmentRental -> campEquipmentRental.getCampEquipmentRentalId().getEquipCdId())
                .toList();

        List<String> equipCdNmList = equipmentMapRegistry.getEquipCdNmListByIds(equipCdIdList);

        //새로 전달된 값과 비교, 기존 DB에 없으면 업데이트 사항O
        for (String value : values) {
            if (!equipCdNmList.contains(value)) {
                return true;
            }
        }

        //기존과 일치한다면 업데이트 해당X
        return false;
    }
}
