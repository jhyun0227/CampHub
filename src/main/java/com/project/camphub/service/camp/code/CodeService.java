package com.project.camphub.service.camp.code;

import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.domain.camp.entity.code.Code;
import com.project.camphub.repository.camp.code.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CodeService {

    private final AmenityCodeRepository amenityCodeRepository;
    private final EquipmentCodeRepository equipmentCodeRepository;
    private final IndustryCodeRepository industryCodeRepository;
    private final InnerAmenityCodeRepository innerAmenityCodeRepository;
    private final LocationCodeRepository locationCodeRepository;
    private final NearbyFacilityCodeRepository nearbyFacilityCodeRepository;
    private final ReservationCodeRepository reservationCodeRepository;
    private final SeasonCodeRepository seasonCodeRepository;
    private final ThemeCodeRepository themeCodeRepository;

    @Transactional(readOnly = true)
    public Map<String, Map<String, Code>> getNameToCodeMap() {
        Map<String, Map<String, Code>> resultMaps = new HashMap<>();

        resultMaps.put("amenityCode", setCodeMaps(amenityCodeRepository.findAll()));
        resultMaps.put("equipmentCode", setCodeMaps(equipmentCodeRepository.findAll()));
        resultMaps.put("industryCode", setCodeMaps(industryCodeRepository.findAll()));
        resultMaps.put("innerAmenityCode", setCodeMaps(innerAmenityCodeRepository.findAll()));
        resultMaps.put("locationCode", setCodeMaps(locationCodeRepository.findAll()));
        resultMaps.put("nearbyFacilityCode", setCodeMaps(nearbyFacilityCodeRepository.findAll()));
        resultMaps.put("reservationCode", setCodeMaps(reservationCodeRepository.findAll()));
        resultMaps.put("seasonCode", setCodeMaps(seasonCodeRepository.findAll()));
        resultMaps.put("themeCode", setCodeMaps(themeCodeRepository.findAll()));

        return resultMaps;
    }

    private Map<String, Code> setCodeMaps(List<? extends Code> codeList) {
        Map<String, Code> resultMaps = new HashMap<>();

        codeList.forEach(code -> {
            resultMaps.put(code.getCodeNm(), code);
        });

        return resultMaps;
    }
}

