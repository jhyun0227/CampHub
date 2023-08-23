package com.project.camphub.camp.repository;

import com.project.camphub.camp.dto.SearchCampRequestDto;
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
    public Page<Camp> findCampList(SearchCampRequestDto searchCampRequestDto, Pageable pageable) {
        List<Camp> campList = jpaQueryFactory
                .selectFrom(camp)
                .join(camp.campDetail, campDetail).fetchJoin()
                .join(camp.campFacility, campFacility).fetchJoin()
                .join(camp.campSite, campSite).fetchJoin()
                .where(
                        facltNmCond(searchCampRequestDto.getFacltNm())
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
}
