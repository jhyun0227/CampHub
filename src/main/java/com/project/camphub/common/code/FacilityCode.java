package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class FacilityCode {

    private Map<String, String> facilityCodeMap;

    @PostConstruct
    public void facilityCodeSet() {
        facilityCodeMap = new HashMap<>();

        facilityCodeMap.put("el", "전기");
        facilityCodeMap.put("wi", "무선인터넷");
        facilityCodeMap.put("fw", "장작판매");
        facilityCodeMap.put("hw", "온수");
        facilityCodeMap.put("tp", "트렘폴린");
        facilityCodeMap.put("sp", "물놀이장");
        facilityCodeMap.put("pg", "놀이터");
        facilityCodeMap.put("tr", "산책로");
        facilityCodeMap.put("st", "운동장");
        facilityCodeMap.put("ex", "운동시설");
        facilityCodeMap.put("sh", "마트.편의점");
    }
}
