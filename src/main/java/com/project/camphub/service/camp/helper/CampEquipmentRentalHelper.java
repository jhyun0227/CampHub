package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampEquipmentRental;
import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import com.project.camphub.domain.camp.registry.EquipmentMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampEquipmentRentalRepository;
import com.project.camphub.repository.camp.code.EquipmentCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampEquipmentRentalHelper implements CampAssociationHelper<CampEquipmentRental, EquipmentCode> {

    private final EquipmentMapRegistry equipmentMapRegistry;
    private final EquipmentCodeRepository equipmentCodeRepository;
    private final CampEquipmentRentalRepository campEquipmentRentalRepository;

    @Override
    public List<CampEquipmentRental> getCampAssociationEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getEqpmnLendCl());
        if (values == null) {
            return null;
        }

        List<CampEquipmentRental> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<EquipmentCode> equipmentCode = Optional.ofNullable(equipmentMapRegistry.findByEquipCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (equipmentCode.isEmpty()) {
                EquipmentCode saveEquipmentCode = new EquipmentCode(value);
                saveCode(saveEquipmentCode);
                addCodeToMap(saveEquipmentCode);

                resultList.add(createCampAssociation(camp, saveEquipmentCode));
            } else {
                resultList.add(createCampAssociation(camp, equipmentCode.get()));
            }
        }

        return resultList;
    }

    @Override
    public void saveCode(EquipmentCode code) {
        equipmentCodeRepository.save(code);
        log.info("CampEquipmentRentalHelper.saveCode 실행, id={}, name={}", code.getEquipCdId(), code.getEquipCdNm());
    }

    @Override
    public void addCodeToMap(EquipmentCode code) {
        equipmentMapRegistry.addEquipmentCodeMaps(code);
    }

    @Override
    public void saveCampAssociation(List<CampEquipmentRental> CampAssociationList) {
        campEquipmentRentalRepository.saveAll(CampAssociationList);
    }

    @Override
    public CampEquipmentRental createCampAssociation(Camp camp, EquipmentCode code) {
        return CampEquipmentRental.createCampEquipmentRental(camp, code);
    }
}
