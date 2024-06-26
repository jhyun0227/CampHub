package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.EquipmentCode;
import com.project.camphub.repository.camp.code.EquipmentCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class EquipmentMapRegistry {

    private final EquipmentCodeRepository equipmentCodeRepository;

    private final Map<Long, EquipmentCode> equipCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("EquipmentMapRegistry.init() 실행");

        List<EquipmentCode> equipCdList = equipmentCodeRepository.findAll();

        setEquipmentCodeMaps(equipCdList);

        log.info("equipCdMap.size()={}", equipCdMap.size());
        log.info("EquipmentMapRegistry.init() 종료");
    }

    private void setEquipmentCodeMaps(List<EquipmentCode> equipCdList) {
        equipCdList.forEach(equipCd ->
                equipCdMap.put(equipCd.getEquipCdId(), equipCd));
    }

    public List<String> getEquipCdNmList(List<Long> equipCdIdList) {
        if (equipCdIdList.isEmpty()) {
            return null;
        }

        return equipCdIdList.stream()
                .map(equipCdId -> equipCdMap.get(equipCdId).getEquipCdNm())
                .toList();
    }
}
