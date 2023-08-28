package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class LocationCode {

    private Map<String, String> locationCodeMap;

    @PostConstruct
    public void LocationCodeSet() {
        locationCodeMap = new HashMap<>();

        locationCodeMap.put("be", "해변");
        locationCodeMap.put("is", "섬");
        locationCodeMap.put("mo", "산");
        locationCodeMap.put("fo", "숲");
        locationCodeMap.put("va", "계곡");
        locationCodeMap.put("ri", "강");
        locationCodeMap.put("la", "호수");
        locationCodeMap.put("dt", "도심");
    }
}
