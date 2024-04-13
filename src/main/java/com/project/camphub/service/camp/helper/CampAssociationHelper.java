package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;

import java.util.List;

public interface CampAssociationHelper<T, D> {

    List<T> getCampAssociationEntity(OpenApiResponse.Item item, Camp camp);
    void saveCode(D code);
    void addCodeToMap(D code);
    void saveCampAssociation(List<T> campAssociationList);
    T createCampAssociation(Camp camp, D code);

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
