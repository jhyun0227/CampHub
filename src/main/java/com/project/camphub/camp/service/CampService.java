package com.project.camphub.camp.service;

import com.project.camphub.camp.dto.CampDto;
import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.dto.SearchCampListResponseDto;
import com.project.camphub.camp.entity.Camp;
import com.project.camphub.camp.repository.CampRepository;
import com.project.camphub.common.Constants;
import com.project.camphub.common.code.*;
import com.project.camphub.common.exception.camp.CampError;
import com.project.camphub.common.exception.camp.CampException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CampService {

    private final CampRepository campRepository;

    //공통코드
    private final AreaCode areaCode;
    private final FacilityCode facilityCode;
    private final FacltDivCode facltDivCode;
    private final IndutyCode indutyCode;
    private final LocationCode locationCode;
    private final ThemaEnvironmentCode themaEnvironmentCode;

    /**
     * 캠프 리스트를 조회하는 메서드
     */
    @Transactional(readOnly = true)
    public Page<SearchCampListResponseDto> findCampList(SearchCampListRequestDto searchCampListRequestDto) {

        //Cd -> Nm 변환
        this.cdToNm(searchCampListRequestDto);
        log.info("searchCampListRequestDto = {}", searchCampListRequestDto);

        PageRequest pageRequest = PageRequest.of(searchCampListRequestDto.getPage(), Constants.SIZE_OF_PAGE);

        //Entity -> Dto
        Page<Camp> campPage = campRepository.findCampList(searchCampListRequestDto, pageRequest);
        List<Camp> campList = campPage.getContent();

        List<SearchCampListResponseDto> searchCampListResponseDtos = campList.stream()
                .map(SearchCampListResponseDto::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(searchCampListResponseDtos, campPage.getPageable(), campPage.getTotalElements());
    }

    /**
     * cpId로 단건의 캠핑장을 조회하는 메서드
     */
    @Transactional(readOnly = true)
    public CampDto findCampInfo(String cpId) {

        Camp findCamp = campRepository.findByCpId(cpId)
                .orElseThrow(() -> new CampException(CampError.NOT_EXIST_CAMP));

        return CampDto.fromEntity(findCamp);
    }


    /**
     * SearchCampListRequestDto의 Cd 필드들을 Nm 필드로 바꾸는 메서드
     */
    private void cdToNm(SearchCampListRequestDto searchCampListRequestDto){
        //도 코드
        List<String> doCdList = searchCampListRequestDto.getDoCdList();
        if (doCdList != null || !doCdList.isEmpty()) {
            List<String> doNmList = searchCampListRequestDto.getDoNmList();
            doCdList.forEach(doCd -> doNmList.add(areaCode.getDoCodeMap().get(doCd)));
        }

        //입지구분(자연환경)
        List<String> lctClCdList = searchCampListRequestDto.getLctClCdList();
        if (lctClCdList != null || !lctClCdList.isEmpty()) {
            List<String> lctClNmList = searchCampListRequestDto.getLctClNmList();
            lctClCdList.forEach(lctClCd -> lctClNmList.add(locationCode.getLocationCodeMap().get(lctClCd)));
        }

        //사업주체(운영형태)
        List<String> facltDivCdList = searchCampListRequestDto.getFacltDivCdList();
        if (facltDivCdList != null || !facltDivCdList.isEmpty()) {
            List<String> facltDivNmList = searchCampListRequestDto.getFacltDivNmList();
            facltDivCdList.forEach(facltDivCd -> facltDivNmList.add(facltDivCode.getFacltDivCodeMap().get(facltDivCd)));
        }

        //업종(주요시설)
        List<String> indutyCdList = searchCampListRequestDto.getIndutyCdList();
        if (indutyCdList != null || !indutyCdList.isEmpty()) {
            List<String> indutyNmList = searchCampListRequestDto.getIndutyNmList();
            indutyCdList.forEach(indutyCd -> indutyNmList.add(indutyCode.getIndutyCodeMap().get(indutyCd)));
        }

        //테마환경
        List<String> themaEnvironmentCdList = searchCampListRequestDto.getThemaEnvironmentCdList();
        if (themaEnvironmentCdList != null || !themaEnvironmentCdList.isEmpty()) {
            List<String> themaEnvironmentNmList = searchCampListRequestDto.getThemaEnvironmentNmList();
            themaEnvironmentCdList.forEach(themaEnvironmentCd -> themaEnvironmentNmList.add(themaEnvironmentCode.getThemaEnvironmentCodeMap().get(themaEnvironmentCd)));
        }

        //부대시설
        List<String> facilityCdList = searchCampListRequestDto.getFacilityCdList();
        if (facilityCdList != null || !facilityCdList.isEmpty()) {
            List<String> facilityNmList = searchCampListRequestDto.getFacilityNmList();
            facilityCdList.forEach(facilityCd -> facilityNmList.add(facilityCode.getFacilityCodeMap().get(facilityCd)));
        }
    }
}