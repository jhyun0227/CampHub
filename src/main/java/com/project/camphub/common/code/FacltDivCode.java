package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class FacltDivCode {

    private Map<String, String> facltDivCodeMap;

    @PostConstruct
    public void facltDivCodeSet() {
        facltDivCodeMap = new HashMap<>();

        facltDivCodeMap.put("lg", "지자체");
        facltDivCodeMap.put("np", "국립공원");
        facltDivCodeMap.put("nrf", "자연휴양림");
        facltDivCodeMap.put("nl", "국민여가");
        facltDivCodeMap.put("pr", "민간");
    }
}
