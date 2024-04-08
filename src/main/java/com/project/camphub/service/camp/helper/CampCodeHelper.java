package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;

import java.util.List;

public interface CampCodeHelper<T, D> {

    List<T> getCampCodeEntity(OpenApiResponse.Item item, Camp camp);
    void saveCampCode(D code);
    void addCampCodeToMap(D code);

    default String[] convertStringToArray(String stringValue) {
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
