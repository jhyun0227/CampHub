package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampReservation;
import com.project.camphub.domain.camp.entity.code.ReservationCode;
import com.project.camphub.domain.camp.registry.ReservationMapRegistry;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampReservationRepository;
import com.project.camphub.repository.camp.code.ReservationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampReservationHelper implements CampCodeHelper<CampReservation, ReservationCode> {

    private final ReservationMapRegistry reservationMapRegistry;
    private final ReservationCodeRepository reservationCodeRepository;
    private final CampReservationRepository campReservationRepository;

    @Override
    public List<CampReservation> getCampCodeEntity(OpenApiResponse.Item item, Camp camp) {

        String[] values = convertStringToArray(item.getResveCl());
        if (values == null) {
            return null;
        }

        List<CampReservation> resultList = new ArrayList<>();
        for (String value : values) {
            Optional<ReservationCode> reservationCode = Optional.ofNullable(reservationMapRegistry.findByResvCdNm(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (reservationCode.isEmpty()) {
                ReservationCode saveReservationCode = new ReservationCode(value);
                saveCode(saveReservationCode);
                addCodeToMap(saveReservationCode);

                resultList.add(createCampCode(camp, saveReservationCode));
            } else {
                resultList.add(createCampCode(camp, reservationCode.get()));
            }
        }

        return resultList;
    }

    @Override
    @Transactional
    public void saveCode(ReservationCode code) {
        reservationCodeRepository.save(code);
        log.info("CampReservationHelper.saveCampCode 실행, id={}, name={}", code.getResvCdId(), code.getResvCdNm());
    }

    @Override
    public void addCodeToMap(ReservationCode code) {
        reservationMapRegistry.addReservationCodeMaps(code);
    }

    @Override
    public void saveCampCode(List<CampReservation> campCodeList) {
        campReservationRepository.saveAll(campCodeList);
    }

    @Override
    public CampReservation createCampCode(Camp camp, ReservationCode code) {
        return CampReservation.createCampReservation(camp, code);
    }
}
