package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampEquipmentRental;
import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import com.project.camphub.domain.camp.registry.EquipmentMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.EquipmentCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampEquipmentRentalHelper implements CampCodeHelper<CampEquipmentRental, EquipmentCode> {

    private final EquipmentMapRegistry equipmentMapRegistry;
    private final EquipmentCodeRepository equipmentCodeRepository;

    @Override
    public List<CampEquipmentRental> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getEqpmnLendCl());
        if (values == null) {
            return null;
        }

        List<CampEquipmentRental> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<EquipmentCode> equipmentCode = Optional.ofNullable(equipmentMapRegistry.findByEquipCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (equipmentCode.isEmpty()) {
                //amenityCode 저장 및 Map 추가
                EquipmentCode saveEquipmentCode = new EquipmentCode(value);
                saveCode(saveEquipmentCode);
                addCodeToMap(saveEquipmentCode);

                resultList.add(new CampEquipmentRental(camp, saveEquipmentCode));
            } else {
                resultList.add(new CampEquipmentRental(camp, equipmentCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(EquipmentCode code) {
        log.info("CampEquipmentRentalHelper.saveCampCode 실행, id={}, name={}", code.getEquipCdId(), code.getEquipCdNm());
        equipmentCodeRepository.save(code);
    }

    @Override
    public void addCodeToMap(EquipmentCode code) {
        equipmentMapRegistry.addEquipmentCodeMaps(code);
    }
}
