package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public interface CampAssociationHelper<T, D> {

    void linkCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps);
    Map<String, D> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps);
    void saveCode(D code);

    /**
     * nameToCodeMaps 전체 루프에서 공통적으로 사용하기 때문에 CampCode 추가시 저장해주어야 다른 Item의 저장시에 재사용할 수 있다.
     * nameToCodeMap은 현재 Item에서만 사용, 추가 중복값이 없기 때문에 넣지 않는다.
     */
    void addCodeToMap(D code, Map<String, Map<String, CampCode>> nameToCodeMaps);
    void createCampAssociationAndLinkToCamp(Camp camp, D code);
    void updateCampAssociations(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps);
    boolean checkUpdate(OpenApiResponse.Item item, Camp camp);

    default String[] convertStringToArray(String stringValue) {
        if (!StringUtils.hasText(stringValue)) {
            return null;
        }

        String[] values = stringValue.split(",");

        if (values.length == 0) {
            return null;
        }

        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
        }

        return values;
    }
}
