package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampGlampingInnerAmenity;
import com.project.camphub.domain.camp.entity.code.InnerAmenityCode;
import com.project.camphub.domain.camp.registry.InnerAmenityMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.InnerAmenityCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampGlampingInnerAmenityHelper implements CampCodeHelper<CampGlampingInnerAmenity, InnerAmenityCode>{

    private final InnerAmenityMapRegistry innerAmenityMapRegistry;
    private final InnerAmenityCodeRepository innerAmenityCodeRepository;

    @Override
    public List<CampGlampingInnerAmenity> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getGlampInnerFclty());
        if (values == null) {
            return null;
        }

        List<CampGlampingInnerAmenity> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<InnerAmenityCode> innerAmenityCode = Optional.ofNullable(innerAmenityMapRegistry.findByInnerAmntyCdNm(value));

            if (innerAmenityCode.isEmpty()) {
                InnerAmenityCode saveInnerAmenityCode = new InnerAmenityCode(value);
                saveCode(saveInnerAmenityCode);
                addCodeToMap(saveInnerAmenityCode);

                resultList.add(new CampGlampingInnerAmenity(camp, saveInnerAmenityCode));
            } else {
                resultList.add(new CampGlampingInnerAmenity(camp, innerAmenityCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(InnerAmenityCode code) {
        log.info("CampGlampingInnerAmenityHelper.saveCampCode 실행, id={}, name={}", code.getInnerAmntyCdId(), code.getInnerAmntyCdNm());
        innerAmenityCodeRepository.save(code);
    }

    @Override
    public void addCodeToMap(InnerAmenityCode code) {
        innerAmenityMapRegistry.addInnerAmenityCodeMaps(code);
    }
}