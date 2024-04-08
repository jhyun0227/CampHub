package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampAmenity;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.domain.camp.registry.*;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.AmenityCodeRepository;
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
public class CampAmenityHelper implements CampCodeHelper<CampAmenity, AmenityCode> {

    private final AmenityMapRegistry amenityMapRegistry;
    private final AmenityCodeRepository amenityCodeRepository;

    @Override
    public List<CampAmenity> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getSbrsCl());
        if (values == null) {
            return null;
        }

        List<CampAmenity> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<AmenityCode> amenityCode = Optional.ofNullable(amenityMapRegistry.findByAmntyCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (amenityCode.isEmpty()) {
                //amenityCode 저장 및 Map 추가
                AmenityCode saveAmenityCode = new AmenityCode(value);
                saveCode(saveAmenityCode);
                addCodeToMap(saveAmenityCode);

                resultList.add(new CampAmenity(camp, saveAmenityCode));
            } else {
                resultList.add(new CampAmenity(camp, amenityCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(AmenityCode code) {
        amenityCodeRepository.save(code);
        log.info("CampAmenityHelper.saveCampCode 실행, id={}, name={}", code.getAmntyCdId(), code.getAmntyCdNm());
    }

    @Override
    public void addCodeToMap(AmenityCode code) {
        amenityMapRegistry.addAmenityCodeMaps(code);
    }
}
