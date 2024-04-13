package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampIndustry;
import com.project.camphub.domain.camp.entity.code.IndustryCode;
import com.project.camphub.domain.camp.registry.IndustryMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampIndustryRepository;
import com.project.camphub.repository.camp.code.IndustryCodeRepository;
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
public class CampIndustryHelper implements CampAssociationHelper<CampIndustry, IndustryCode> {

    private final IndustryMapRegistry industryMapRegistry;
    private final IndustryCodeRepository industryCodeRepository;
    private final CampIndustryRepository campIndustryRepository;

    @Override
    public List<CampIndustry> getCampAssociationEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getInduty());
        if (values == null) {
            return null;
        }

        List<CampIndustry> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<IndustryCode> industryCode = Optional.ofNullable(industryMapRegistry.findByIndstCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (industryCode.isEmpty()) {
                IndustryCode saveIndustryCode = new IndustryCode(value);
                saveCode(saveIndustryCode);
                addCodeToMap(saveIndustryCode);

                resultList.add(createCampAssociation(camp, saveIndustryCode));
            } else {
                resultList.add(createCampAssociation(camp, industryCode.get()));
            }
        }

        return resultList;
    }

    @Override
    public void saveCode(IndustryCode code) {
        industryCodeRepository.save(code);
        log.info("CampIndustryHelper.saveCode 실행, id={}, name={}", code.getIndstCdId(), code.getIndstCdNm());
    }

    @Override
    public void addCodeToMap(IndustryCode code) {
        industryMapRegistry.addIndustryCodeMaps(code);
    }

    @Override
    public void saveCampAssociation(List<CampIndustry> CampAssociationList) {
        campIndustryRepository.saveAll(CampAssociationList);
    }

    @Override
    public CampIndustry createCampAssociation(Camp camp, IndustryCode code) {
        return CampIndustry.createCampIndustry(camp, code);
    }
}
