package com.project.camphub.domain.camp.enumeration;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum AnimalEntryType {

    CAN("가능"),
    SMALL("가능(소형견)"),
    CANNOT("불가능");

    private final String description;
    AnimalEntryType(String description) {
        this.description = description;
    }

    private static final Map<String, AnimalEntryType> ANIMAL_ENTRY_TYPE_MAP = new HashMap<>();

    static {
        for (AnimalEntryType animalEntryType : values()) {
            ANIMAL_ENTRY_TYPE_MAP.put(animalEntryType.getDescription(), animalEntryType);
        }
    }

    public static AnimalEntryType findByDescription(String description) {
        return ANIMAL_ENTRY_TYPE_MAP.get(description);
    }
}
