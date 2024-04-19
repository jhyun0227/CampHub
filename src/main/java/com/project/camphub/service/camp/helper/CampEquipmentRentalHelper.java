package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampEquipmentRental;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampEquipmentRentalRepository;
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

    @Override
    public void linkCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getEqpmnLendCl());
        if (values == null) {
            return;
        }

        List<CampEquipmentRental> resultList = new ArrayList<>();

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
        nameToCodeMaps.get(EQUIPMENT_CODE).put(code.getEquipCdNm(), code);
    }

    @Override
    public void createCampAssociationAndLinkToCamp(Camp camp, EquipmentCode code) {
        CampEquipmentRental.createCampEquipmentRentalAndLinkToCamp(camp, code);
    }
}
