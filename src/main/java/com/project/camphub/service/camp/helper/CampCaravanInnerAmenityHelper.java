package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampCaravanInnerAmenity;
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
public class CampCaravanInnerAmenityHelper implements CampCodeHelper<CampCaravanInnerAmenity, InnerAmenityCode>{

    private final InnerAmenityMapRegistry innerAmenityMapRegistry;
    private final InnerAmenityCodeRepository innerAmenityCodeRepository;

    @Override
    public List<CampCaravanInnerAmenity> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getCaravInnerFclty());
        if (values == null) {
            return null;
        }

        List<CampCaravanInnerAmenity> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<InnerAmenityCode> innerAmenityCode = Optional.ofNullable(innerAmenityMapRegistry.findByInnerAmntyCdNm(value));

            if (innerAmenityCode.isEmpty()) {
                InnerAmenityCode saveInnerAmenityCode = new InnerAmenityCode(value);
                saveCode(saveInnerAmenityCode);
                addCodeToMap(saveInnerAmenityCode);

                resultList.add(new CampCaravanInnerAmenity(camp, saveInnerAmenityCode));
            } else {
                resultList.add(new CampCaravanInnerAmenity(camp, innerAmenityCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(InnerAmenityCode code) {
        innerAmenityCodeRepository.save(code);
        log.info("CampCaravanInnerAmenityHelper.saveCampCode 실행, id={}, name={}", code.getInnerAmntyCdId(), code.getInnerAmntyCdNm());
    }

    @Override
    public void addCodeToMap(InnerAmenityCode code) {
        innerAmenityMapRegistry.addInnerAmenityCodeMaps(code);
    }
}
