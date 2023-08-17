package com.project.camphub.externalapi.dto.openapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ItemMapDto {

    List<Item> newCamps;
    Map<String, Item> updatedCamps;

    public ItemMapDto() {
        this.newCamps = new ArrayList<>();
        this.updatedCamps = new HashMap<>();
    }

}
