package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class IndutyCode {

    private Map<String, String> indutyCodeMap;

    @PostConstruct
    public void indutyCodeSet() {
        indutyCodeMap = new HashMap<>();

        indutyCodeMap.put("gnrl", "일반야영장");
        indutyCodeMap.put("auto", "자동차야영장");
        indutyCodeMap.put("glamp", "글램핑");
        indutyCodeMap.put("carav", "카라반");
   }
}
