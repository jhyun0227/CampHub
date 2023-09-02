package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampListRequestDto;
import com.project.camphub.camp.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.project.camphub.camp.entity.QCamp.*;
import static com.project.camphub.camp.entity.QCampDetail.*;
import static com.project.camphub.camp.entity.QCampFacility.*;
import static com.project.camphub.camp.entity.QCampSite.*;

@RequiredArgsConstructor
public class QueryDslCampRepositoryImpl implements QueryDslCampRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Camp> findCampList(SearchCampListRequestDto searchCampListRequestDto, Pageable pageable) {
        List<Camp> campList = jpaQueryFactory
                .selectFrom(camp)
                .join(camp.campDetail, campDetail).fetchJoin()
                .join(camp.campFacility, campFacility).fetchJoin()
                .join(camp.campSite, campSite).fetchJoin()
                .where(
                        facltNmCond(searchCampListRequestDto.getFacltNm()),
                        doNmCond(searchCampListRequestDto.getDoNmList()),
                        sigunguNmCond(searchCampListRequestDto.getSigunguNm()),
                        lctClCond(searchCampListRequestDto.getLctClNmList()),
                        facltDivNmCond(searchCampListRequestDto.getFacltDivNmList()),
                        indutyCond(searchCampListRequestDto.getIndutyNmList()),
                        siteBottomCond(searchCampListRequestDto.getSiteBottomCdList()),
                        themaEnvironmentCond(searchCampListRequestDto.getThemaEnvironmentNmList()),
                        facilityCond(searchCampListRequestDto.getFacilityNmList()),
                        accompanyAnimal(searchCampListRequestDto.getAccpnyAnimYn()),
                        individualCarav(searchCampListRequestDto.getIndivCaravYn()),
                        individualTrler(searchCampListRequestDto.getIndivTrlerYn())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(campList, pageable, 0);
    }

    /**
     * 야영장명 검색조건
     */
    private BooleanExpression facltNmCond(String facltNm) {
        if (!StringUtils.hasText(facltNm)) {
            return null;
        }

        return camp.cpFacltNm.contains(facltNm);
    }

    /**
     * 지역 검색조건
     */
    private BooleanExpression doNmCond(List<String> doNmList) {
        if (doNmList == null || doNmList.isEmpty()) {
            return null;
        }

        BooleanExpression booleanExpression = null;

        for (String doNm : doNmList) {

            System.out.println("doNm = " + doNm);
            BooleanExpression temp = campDetail.cpdDoNm.eq(doNm.trim());

            if (booleanExpression == null) {
                booleanExpression = temp;
            } else {
                booleanExpression = booleanExpression.or(temp);
            }
        }

        System.out.println("booleanExpression.toString() = " + booleanExpression.toString());


        return booleanExpression;
    }
    private BooleanExpression sigunguNmCond(String sigunguNm) {
        if (!StringUtils.hasText(sigunguNm)) {
            return null;
        }

        return campDetail.cpdSigunguNm.eq(sigunguNm);
    }

    /**
     * 입지구분 검색조건
     */
    private BooleanExpression lctClCond(List<String> lctClNmList) {
        if (lctClNmList == null || lctClNmList.isEmpty()) {
            return null;
        }

        BooleanExpression booleanExpression = null;

        for (String lctClNm : lctClNmList) {
            BooleanExpression temp = camp.cpLctCl.contains(lctClNm.trim());

            if (booleanExpression == null) {
                booleanExpression = temp;
            } else {
                booleanExpression = booleanExpression.or(temp);
            }
        }

        return booleanExpression;
    }

    /**
     * 사업주체 검색조건
     */
    private BooleanExpression facltDivNmCond(List<String> facltDivNmList) {
        if (facltDivNmList == null || facltDivNmList.isEmpty()) {
            return null;
        }

        BooleanExpression booleanExpression = null;

        for (String facltDivNm : facltDivNmList) {
            BooleanExpression temp = camp.cpFacltDivNm.eq(facltDivNm.trim());

            if (booleanExpression == null) {
                booleanExpression = temp;
            } else {
                booleanExpression = booleanExpression.or(temp);
            }
        }

        return booleanExpression;
    }

    /**
     * 업종 검색조건
     */
    private BooleanExpression indutyCond(List<String> indutyNmList) {
        if (indutyNmList == null || indutyNmList.isEmpty()) {
            return null;
        }

        BooleanExpression booleanExpression = null;

        for (String indutyNm : indutyNmList) {
            BooleanExpression temp = camp.cpInduty.contains(indutyNm.trim());

            if (booleanExpression == null) {
                booleanExpression = temp;
            } else {
                booleanExpression = booleanExpression.or(temp);
            }
        }

        return booleanExpression;
    }

    /**
     * 사이트바닥 검색조건
     * 사이트바닥의 검색 조건은 cps_bottom_site_cl[n] 값이 0이 아니면 해당시설이 있는 것으로 간주한다.
     */
    private BooleanExpression siteBottomCond(List<String> siteBottomCdList) {
        if (siteBottomCdList == null || siteBottomCdList.isEmpty()) {
            return null;
        }

        BooleanExpression booleanExpression = null;

        for (String siteBottomCd : siteBottomCdList) {
            BooleanExpression temp = null;

            switch (siteBottomCd) {
                case "gr":
                    temp = campSite.cpsSiteBottomCl1.ne("0");
                    break;
                case "cs":
                    temp = campSite.cpsSiteBottomCl2.ne("0");
                    break;
                case "te":
                    temp = campSite.cpsSiteBottomCl3.ne("0");
                    break;
                case "pe":
                    temp = campSite.cpsSiteBottomCl4.ne("0");
                    break;
                case "so":
                    temp = campSite.cpsSiteBottomCl5.ne("0");
                    break;
            }

            if (booleanExpression == null) {
                booleanExpression = temp;
            } else {
                booleanExpression = booleanExpression.or(temp);
            }
        }

        return booleanExpression;
    }

    /**
     * 테마환경 검색조건
     */
    private BooleanExpression themaEnvironmentCond(List<String> themaEnvironmentNmList) {
        if (themaEnvironmentNmList == null || themaEnvironmentNmList.isEmpty()) {
            return null;
        }

        BooleanExpression booleanExpression = null;

        for (String themaEnvironmentNm : themaEnvironmentNmList) {
            BooleanExpression temp = camp.cpThemaEnvrnCl.contains(themaEnvironmentNm.trim());

            if (booleanExpression == null) {
                booleanExpression = temp;
            } else {
                booleanExpression = booleanExpression.or(temp);
            }
        }

        return booleanExpression;
    }

    /**
     * 부대시설 검색조건
     */
    private BooleanExpression facilityCond(List<String> facilityNmList) {
        if (facilityNmList == null || facilityNmList.isEmpty()) {
            return null;
        }

        BooleanExpression booleanExpression = null;

        for (String facilityNm : facilityNmList) {
            BooleanExpression temp = campFacility.cpfSbrsCl.contains(facilityNm.trim());

            if (booleanExpression == null) {
                booleanExpression = temp;
            } else {
                booleanExpression = booleanExpression.or(temp);
            }
        }

        return booleanExpression;
    }

    //기타정보
    private BooleanExpression accompanyAnimal(String accpnyAnimYn) {
        if (!StringUtils.hasText(accpnyAnimYn)) {
            return null;
        } else if ("N".equals(accpnyAnimYn)) {
            return null;
        }

        return campDetail.cpdAnimalCmgCl.in("가능", "가능(소형견)");
    }
    private BooleanExpression individualCarav(String indivCaravYn) {
        if (!StringUtils.hasText(indivCaravYn)) {
            return null;
        }

        return campDetail.cpdCaravAcmpnyAt.eq(indivCaravYn);
    }
    private BooleanExpression individualTrler(String indivTrlerYn) {
        if (!StringUtils.hasText(indivTrlerYn)) {
            return null;
        }

        return campDetail.cpdTrlerAcmpnyAt.eq(indivTrlerYn);
    }
}
