package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.repository.camp.code.AmenityCodeRepository;
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
public class AmenityMapRegistry {

    private final AmenityCodeRepository amenityCodeRepository;

    private final Map<Long, AmenityCode> amntyCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("AmenityMapRegistry.init() 실행");

        List<AmenityCode> amntyCdList = amenityCodeRepository.findAll();

        setAmenityCodeMaps(amntyCdList);

        log.info("amntyCdMap.size()={}", amntyCdMap.size());
        log.info("AmenityMapRegistry.init() 종료");
    }

    private void setAmenityCodeMaps(List<AmenityCode> amntyCdList) {
        amntyCdList.forEach(amntyCd ->
                amntyCdMap.put(amntyCd.getAmntyCdId(), amntyCd));
    }

    public List<String> getAmntyCdNmList(List<Long> amntyCdIdList) {
        if (amntyCdIdList.isEmpty()) {
            return null;
        }

        return amntyCdIdList.stream()
                .map(amntyCdId -> amntyCdMap.get(amntyCdId).getAmntyCdNm())
                .toList();
    }
}
