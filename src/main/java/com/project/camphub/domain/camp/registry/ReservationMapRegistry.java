package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.ReservationCode;
import com.project.camphub.repository.camp.code.ReservationCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class ReservationMapRegistry {

    private final ReservationCodeRepository reservationCodeRepository;

    private final Map<Long, ReservationCode> resvCdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("ReservationMapRegistry.init() 실행");

        List<ReservationCode> resvCdList = reservationCodeRepository.findAll();

        setReservationCodeMaps(resvCdList);

        log.info("resvCdMap.size()={}", resvCdMap.size());
        log.info("ReservationMapRegistry.init() 종료");
    }

    private void setReservationCodeMaps(List<ReservationCode> resvCdList) {
        resvCdList.forEach(resvCd ->
                resvCdMap.put(resvCd.getResvCdId(), resvCd));
    }

    public List<String> getResvCdNmList(List<Long> resvCdIdList) {
        if (resvCdIdList.isEmpty()) {
            return null;
        }

        return resvCdIdList.stream()
                .map(resvCdId -> resvCdMap.get(resvCdId).getResvCdNm())
                .toList();
    }
}
