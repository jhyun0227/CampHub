package com.project.camphub.service.camp.helper;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampReservation;
import com.project.camphub.domain.camp.entity.code.CampCode;
import com.project.camphub.domain.camp.entity.code.ReservationCode;
import com.project.camphub.domain.openapi.dto.OpenApiResponse;
import com.project.camphub.repository.camp.associations.CampReservationRepository;
import com.project.camphub.repository.camp.code.ReservationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.project.camphub.domain.camp.CampCodeConst.RESERVATION_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampReservationHelper implements CampAssociationHelper<CampReservation, ReservationCode> {

    private final ReservationCodeRepository reservationCodeRepository;
    private final CampReservationRepository campReservationRepository;

    @Override
    public List<CampReservation> getCampAssociationEntity(OpenApiResponse.Item item, Camp camp, Map<String, Map<String, CampCode>> nameToCodeMaps) {

        String[] values = convertStringToArray(item.getResveCl());
        if (values == null) {
            return null;
        }

        List<CampReservation> resultList = new ArrayList<>();

        Map<String, ReservationCode> nameToCodeMap = getNameToCodeMap(nameToCodeMaps);
        for (String value : values) {
            Optional<ReservationCode> reservationCode = Optional.ofNullable(nameToCodeMap.get(value));

            //기존 Map에 없는 값일 경우 DB에 코드 추가 후, Map에 해당 객체 추가
            if (reservationCode.isEmpty()) {
                ReservationCode saveReservationCode = new ReservationCode(value);
                saveCode(saveReservationCode);
                addCodeToMap(saveReservationCode, nameToCodeMaps);

                resultList.add(createCampAssociation(camp, saveReservationCode));
            } else {
                resultList.add(createCampAssociation(camp, reservationCode.get()));
            }
        }

        return resultList;
    }

    @Override
    public Map<String, ReservationCode> getNameToCodeMap(Map<String, Map<String, CampCode>> nameToCodeMaps) {
        Map<String, ReservationCode> resultMaps = new HashMap<>();

        Map<String, CampCode> findCodeMaps = nameToCodeMaps.get(RESERVATION_CODE);

        for (Map.Entry<String, CampCode> entry : findCodeMaps.entrySet()) {
            resultMaps.put(entry.getKey(), (ReservationCode) entry.getValue());
        }

        return resultMaps;
    }

    @Override
    public void saveCode(ReservationCode code) {
        reservationCodeRepository.save(code);
        log.info("CampReservationHelper.saveCode 실행, id={}, name={}", code.getResvCdId(), code.getResvCdNm());
    }

    @Override
    public void addCodeToMap(ReservationCode code, Map<String, Map<String, CampCode>> nameToCodeMaps) {
        nameToCodeMaps.get(RESERVATION_CODE).put(code.getResvCdNm(), code);
    }

    @Override
    public void saveCampAssociation(List<CampReservation> CampAssociationList) {
        campReservationRepository.saveAll(CampAssociationList);
    }

    @Override
    public CampReservation createCampAssociation(Camp camp, ReservationCode code) {
        return CampReservation.createCampReservation(camp, code);
    }
}
