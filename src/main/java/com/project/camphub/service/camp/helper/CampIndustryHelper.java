package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampIndustry;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.IndustryCode;
import com.project.camphub.domain.camp.registry.IndustryMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.code.IndustryCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.INDUSTRY_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampIndustryHelper implements CampAssociationHelper<CampIndustry, IndustryCode> {

    private final IndustryCodeRepository industryCodeRepository;
    private final IndustryMapRegistry industryMapRegistry;

    @Override
    public void linkCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getInduty());
        if (values == null) {
            return;
        }

        Map<String, IndustryCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<IndustryCode> industryCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (industryCode.isEmpty()) {
                IndustryCode saveIndustryCode = IndustryCode.createIndustryCode(value);
                saveCode(saveIndustryCode);
                addCodeToMap(saveIndustryCode, nameToCodeMaps);

                createCampAssociationAndLinkToCamp(camp, saveIndustryCode);
            } else {
                createCampAssociationAndLinkToCamp(camp, industryCode.get());
            }
        }
    }

    @Override
    public Map<String, IndustryCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, IndustryCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(INDUSTRY_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (IndustryCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(IndustryCode code) {
        industryCodeRepository.save(code);
        log.info("CampIndustryHelper.saveCode 실행, id={}, name={}", code.getIndstCdId(), code.getIndstCdNm());
    }

    @Override
    public void addCodeToMap(IndustryCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        industryMapRegistry.getIndstCdMap().put(code.getIndstCdId(), code);

        nameToCodeMaps.get(INDUSTRY_CODE).put(code.getIndstCdNm(), code);
    }

    @Override
    public void createCampAssociationAndLinkToCamp(Camp camp, IndustryCode code) {
        CampIndustry.createCampIndustryAndLinkToCamp(camp, code);
    }

    @Override
    public void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        if (!checkUpdate(item, camp)) {
            return;
        }

        //초기화
        camp.resetCampIndustryList();

        //재설정
        linkCampAssociations(item, camp, nameToCodeMaps);
    }

    @Override
    public boolean checkUpdate(OpenApiResponse.Item item, Camp camp) {
        List<CampIndustry> campIndustryList = camp.getCampIndustryList();
        String[] values = convertStringToArray(item.getInduty());

        if (values == null) {
            if (campIndustryList.size() != 0) {
                camp.resetCampIndustryList();
            }

            return false;
        }

        if (values.length != campIndustryList.size()) {
            return true;
        }

        List<Long> indstCdIdList = campIndustryList.stream()
                .map(campIndustry -> campIndustry.getCampIndustryId().getIndstCdId())
                .toList();

        List<String> indstCdNmList = industryMapRegistry.getIndstCdNmListByIds(indstCdIdList);

        for (String value : values) {
            if (!indstCdNmList.contains(value)) {
                return true;
            }
        }

        return false;
    }
}
