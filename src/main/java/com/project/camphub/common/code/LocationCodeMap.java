package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class LocationCodeMap {

    private Map<String, String> locationMap;

    @PostConstruct
    public void environmentCodeSet() {
        locationMap = new HashMap<>();

        locationMap.put("be", "해변");
        locationMap.put("is", "섬");
        locationMap.put("mo", "산");
        locationMap.put("fo", "숲");
        locationMap.put("va", "계곡");
        locationMap.put("ri", "강");
        locationMap.put("la", "호수");
        locationMap.put("dt", "도심");
    }
}
