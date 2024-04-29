package com.project.camphub.service.camp;

import com.project.camphub.common.dto.ResponseDto;
import com.project.camphub.common.dto.enumaration.ResponseCode;
import com.project.camphub.domain.camp.dto.CampDto;
import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.registry.*;
import com.project.camphub.exception.camp.CampNotFoundException;
import com.project.camphub.repository.camp.CampRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;

    private final AmenityMapRegistry amenityMapRegistry;
    private final EquipmentMapRegistry equipmentMapRegistry;
    private final IndustryMapRegistry industryMapRegistry;
    private final InnerAmenityMapRegistry innerAmenityMapRegistry;
    private final LocationMapRegistry locationMapRegistry;
    private final NearbyFacilityMapRegistry nearbyFacilityMapRegistry;
    private final ReservationMapRegistry reservationMapRegistry;
    private final SeasonMapRegistry seasonMapRegistry;
    private final ThemeMapRegistry themeMapRegistry;

    public ResponseDto<CampDto> findById(String cpId) {

        Optional<Camp> optionalCamp = campRepository.findByCpId(cpId);

        if (optionalCamp.isEmpty()) {
            throw new CampNotFoundException("존재하지 않는 캠핑장입니다.");
        }

        Camp camp = optionalCamp.get();
        camp.addReadCount();

        CampDto campDto = CampDto.entityToDto(camp);

        setAssociations(camp, campDto);

        return ResponseDto.success(ResponseCode.CODE_200, campDto);
    }

    /**
     * 연관관계 셋팅...
     * 연관관계마다 쿼리가 나가는데 이거 어떻게 해야할지.. 고민 필요
     */
    private void setAssociations(Camp camp, CampDto campDto) {
        campDto.setCampAmenityNmList(amenityMapRegistry.getAmntyCdNmList(camp.getCampAmenityIdList()));
        campDto.setCampCaravanInnerAmenityNmList(innerAmenityMapRegistry.getInnerAmntyCdNmList(camp.getCampCaravanInnerAmenityIdList()));
        campDto.setCampEquipmentRentalNmList(equipmentMapRegistry.getEquipCdNmList(camp.getCampEquipmentRentalIdList()));
        campDto.setCampGlampingInnerAmenityNmList(innerAmenityMapRegistry.getInnerAmntyCdNmList(camp.getCampGlampingInnerAmenityIdList()));
        campDto.setCampIndustryNmList(industryMapRegistry.getIndstCdNmList(camp.getCampIndustryIdList()));
        campDto.setCampLocationNmList(locationMapRegistry.getLocdCdNmList(camp.getCampLocationIdList()));
        campDto.setCampNearbyFacilityNmList(nearbyFacilityMapRegistry.getNrbyFcltCdNmList(camp.getCampNearbyFacilityIdList()));
        campDto.setCampOperationSeasonNmList(seasonMapRegistry.getSeasonCdNmList(camp.getCampOperationSeasonIdList()));
        campDto.setCampReservationNmList(reservationMapRegistry.getResvCdNmList(camp.getCampReservationIdList()));
        campDto.setCampThemeNmList(themeMapRegistry.getThemeCdNmList(camp.getCampThemeIdList()));
        campDto.setCampTravelSeasonNmList(seasonMapRegistry.getSeasonCdNmList(camp.getCampTravelSeasonIdList()));
    }
}
