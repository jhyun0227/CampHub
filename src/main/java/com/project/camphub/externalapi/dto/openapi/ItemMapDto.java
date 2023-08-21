package com.project.camphub.externalapi.dto.openapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ItemMapDto {

    Map<String, Item> newCampItems;
    Map<String, Item> updatedCampItems;

    public ItemMapDto() {
        this.newCampItems = new HashMap<>();
        this.updatedCampItems = new HashMap<>();
    }

}
