package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class EnvironmentMap {

    private Map<String, String> environmentMap;

    @PostConstruct
    public void environmentCodeSet() {
        environmentMap = new HashMap<>();

        environmentMap.put("be", "해변");
        environmentMap.put("is", "섬");
        environmentMap.put("mo", "산");
        environmentMap.put("fo", "숲");
        environmentMap.put("va", "계곡");
        environmentMap.put("ri", "강");
        environmentMap.put("la", "호수");
        environmentMap.put("dt", "도심");
    }
}
