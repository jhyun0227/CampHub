package com.project.camphub.repository.camp.code;

import com.project.camphub.domain.camp.dto.CampSearchRequest;
import com.project.camphub.domain.camp.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.project.camphub.domain.camp.entity.QCamp.*;
import static com.project.camphub.domain.camp.entity.QCampDetail.*;
import static com.project.camphub.domain.camp.entity.QCampFacility.*;
import static com.project.camphub.domain.camp.entity.QCampSite.*;

@Repository
@RequiredArgsConstructor
public class QuerydslCampRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Camp> findCampList(CampSearchRequest campSearchRequest) {
        return jpaQueryFactory
                .selectFrom(camp)
                .join(camp.campDetail, campDetail).fetchJoin()
                .join(camp.campFacility, campFacility).fetchJoin()
                .join(camp.campSite, campSite).fetchJoin()
                .where(campNameContains(campSearchRequest.getCampName()))
                .orderBy(camp.cpModDt.desc())
                .fetch();
    }

    private BooleanExpression campNameContains(String campName) {
        if (!StringUtils.hasText(campName)) {
            return null;
        }

        return camp.cpName.contains(campName);
    }
}
