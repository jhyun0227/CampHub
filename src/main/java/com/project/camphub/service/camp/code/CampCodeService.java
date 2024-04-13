package com.project.camphub.service.camp.code;

import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.repository.camp.code.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.camphub.domain.camp.CampCodeConst.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampCodeService {

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
    public Map<String, Map<String, CampCode>> getNameToCodeMaps() {
        Map<String, Map<String, CampCode>> resultMaps = new HashMap<>();

        resultMaps.put(AMENITY_CODE, setCodeMaps(amenityCodeRepository.findAll()));
        resultMaps.put(EQUIPMENT_CODE, setCodeMaps(equipmentCodeRepository.findAll()));
        resultMaps.put(INDUSTRY_CODE, setCodeMaps(industryCodeRepository.findAll()));
        resultMaps.put(INNER_AMENITY_CODE, setCodeMaps(innerAmenityCodeRepository.findAll()));
        resultMaps.put(LOCATION_CODE, setCodeMaps(locationCodeRepository.findAll()));
        resultMaps.put(NEARBY_FACILITY_CODE, setCodeMaps(nearbyFacilityCodeRepository.findAll()));
        resultMaps.put(RESERVATION_CODE, setCodeMaps(reservationCodeRepository.findAll()));
        resultMaps.put(SEASON_CODE, setCodeMaps(seasonCodeRepository.findAll()));
        resultMaps.put(THEME_CODE, setCodeMaps(themeCodeRepository.findAll()));

        return resultMaps;
    }

    private Map<String, CampCode> setCodeMaps(List<? extends CampCode> codeList) {
        Map<String, CampCode> resultMaps = new HashMap<>();

        codeList.forEach(code -> {
            resultMaps.put(code.getCodeNm(), code);
        });

        return resultMaps;
    }
}

