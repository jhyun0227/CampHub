package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import com.project.camphub.repository.camp.code.InnerAmenityCodeRepository;
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
public class InnerAmenityMapRegistry {

    private final InnerAmenityCodeRepository innerAmenityCodeRepository;

    private final Map<Long, InnerAmenityCode> innerAmntyCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("InnerAmenityMapRegistry.init() 실행");

        List<InnerAmenityCode> innerAmntyCdList = innerAmenityCodeRepository.findAll();

        setInnerAmenityCodeMaps(innerAmntyCdList);

        log.info("innerAmntyCdMap.size()={}", innerAmntyCdMap.size());
        log.info("InnerAmenityMapRegistry.init() 종료");
    }

    private void setInnerAmenityCodeMaps(List<InnerAmenityCode> innerAmntyCdList) {
        innerAmntyCdList.forEach(innerAmntyCd ->
                innerAmntyCdMap.put(innerAmntyCd.getInnerAmntyCdId(), innerAmntyCd));
    }

    public List<String> getInnerAmntyCdNmList(List<Long> innerAmntyCdIdList) {
        if (innerAmntyCdIdList.isEmpty()) {
            return null;
        }

        return innerAmntyCdIdList.stream()
                .map(innerAmntyCdId -> innerAmntyCdMap.get(innerAmntyCdId).getInnerAmntyCdNm())
                .toList();
    }
}
