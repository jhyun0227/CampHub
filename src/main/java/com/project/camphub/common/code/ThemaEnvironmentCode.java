package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class ThemaEnvironmentCode {

    private Map<String, String> themaEnvironmentCodeMap;

    @PostConstruct
    public void themaEnvironmentCodeSet() {
        themaEnvironmentCodeMap = new HashMap<>();

        themaEnvironmentCodeMap.put("sr", "일출명소");
        themaEnvironmentCodeMap.put("ss", "일몰명소");
        themaEnvironmentCodeMap.put("wl", "수상레저");
        themaEnvironmentCodeMap.put("al", "항공레저");
        themaEnvironmentCodeMap.put("sk", "스키");
        themaEnvironmentCodeMap.put("fi", "낚시");
        themaEnvironmentCodeMap.put("ac", "액티비티");
        themaEnvironmentCodeMap.put("sp", "봄꽃여행");
        themaEnvironmentCodeMap.put("su", "여름물놀이");
        themaEnvironmentCodeMap.put("au", "가을단풍명소");
        themaEnvironmentCodeMap.put("wi", "겨울눈꽃명소");
        themaEnvironmentCodeMap.put("wa", "걷기길");
    }
}
