package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import com.project.camphub.repository.camp.code.EquipmentCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class EquipmentMapRegistry {

    private final EquipmentCodeRepository equipmentCodeRepository;

    private final Map<Long, EquipmentCode> equipCdMap = new HashMap<>();
//    private final Map<String, EquipmentCode> nameToEquipCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("EquipmentMapRegistry.init() 실행");

        List<EquipmentCode> equipCdList = equipmentCodeRepository.findAll();

        setEquipmentCodeMaps(equipCdList);

        log.info("equipCdMap.size()={}", equipCdMap.size());
//        log.info("nameToEquipCdMap.size()={}", nameToEquipCdMap.size());
        log.info("EquipmentMapRegistry.init() 종료");
    }

    private void setEquipmentCodeMaps(List<EquipmentCode> equipCdList) {
        equipCdList.forEach(equipCd -> {
            equipCdMap.put(equipCd.getEquipCdId(), equipCd);
//            nameToEquipCdMap.put(equipCd.getEquipCdNm(), equipCd);
        });
    }

    public EquipmentCode findByEquipCdId(Long equipCdId) {
        return equipCdMap.get(equipCdId);
    }

    /*
    public EquipmentCode findByEquipCdNm(String equipCdNm) {
        return nameToEquipCdMap.get(equipCdNm);
    }

    public void addEquipmentCodeMaps(EquipmentCode equipmentCode) {
        equipCdMap.put(equipmentCode.getEquipCdId(), equipmentCode);
        nameToEquipCdMap.put(equipmentCode.getEquipCdNm(), equipmentCode);
    }
    */

    public List<String> getEquipCdNmListByIds(List<Long> equipCdIdList) {
        return equipCdIdList.stream()
                .map(equipCdId -> equipCdMap.get(equipCdId).getEquipCdNm())
                .collect(Collectors.toList());
    }
}
