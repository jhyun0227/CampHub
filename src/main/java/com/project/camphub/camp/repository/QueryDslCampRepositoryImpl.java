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
                        doNmCond(searchCampListRequestDto.getDoNm()),
                        sigunguNmCond(searchCampListRequestDto.getSigunguNm()),
                        lctClCond(searchCampListRequestDto.getLctClNm())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(campList, pageable, 0);
    }

    /**
     * 야영장명 검색 조건
     */
    private BooleanExpression facltNmCond(String facltNm) {
        if (!StringUtils.hasText(facltNm)) {
            return null;
        }

        return camp.cpFacltNm.contains(facltNm);
    }

    /**
     * 지역 검색 조건
     */
    private BooleanExpression doNmCond(String doNm) {
        if (!StringUtils.hasText(doNm)) {
            return null;
        }

        return campDetail.cpdDoNm.eq(doNm);
    }
    private BooleanExpression sigunguNmCond(String sigunguNm) {
        if (!StringUtils.hasText(sigunguNm)) {
            return null;
        }

        return campDetail.cpdSigunguNm.eq(sigunguNm);
    }

    /**
     * 입지구분 검색 조건
     */
    private BooleanExpression lctClCond(String lctClNm) {
        if (!StringUtils.hasText(lctClNm)) {
            return null;
        }

        return camp.cpLctCl.contains(lctClNm);
    }
}
