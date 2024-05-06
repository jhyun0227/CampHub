package com.project.camphub.repository.camp;

import com.project.camphub.common.dto.enumaration.YnType;
import com.project.camphub.domain.camp.dto.CampSearchCondDto;
import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.enumeration.ManagingStatusType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.project.camphub.domain.camp.entity.QCamp.camp;
import static com.project.camphub.domain.camp.entity.QCampDetail.campDetail;
import static com.project.camphub.domain.camp.entity.QCampFacility.campFacility;
import static com.project.camphub.domain.camp.entity.QCampSite.campSite;
import static com.project.camphub.domain.camp.entity.associations.QCampAmenity.campAmenity;
import static com.project.camphub.domain.camp.entity.code.QAmenityCode.amenityCode;
import static com.project.camphub.domain.common.entity.area.QDistrictCode.districtCode;
import static com.project.camphub.domain.common.entity.area.QProvinceCode.provinceCode;

@Repository
@RequiredArgsConstructor
public class QuerydslCampRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * CampFacility, CampSite는 조인이 필요없지만
     * OneToOne 연관관계에서의 지연로딩이 동작하지않는 이슈로 인해 코드 추가하였다.
     * 추후 성능 개선의 여지가 있는 경우 수정 필요하다.
     */
    public List<Camp> findCampListByCond(CampSearchCondDto campSearchCondDto) {
        return jpaQueryFactory
                .selectFrom(camp)
                .leftJoin(camp.campDetail, campDetail).fetchJoin()
                .leftJoin(camp.campFacility, campFacility).fetchJoin()
                .leftJoin(camp.campSite, campSite).fetchJoin()
                .leftJoin(camp.provinceCode, provinceCode).fetchJoin()
                .leftJoin(camp.districtCode, districtCode).fetchJoin()
                .leftJoin(camp.campAmenityList, campAmenity).fetchJoin()
                .leftJoin(campAmenity.amenityCode, amenityCode).fetchJoin()
                .where(
                        camp.cpIsActive.eq(YnType.Y),
                        campDetail.cpdMngStatType.ne(ManagingStatusType.SHUTDOWN),
                        campNameContains(campSearchCondDto.getCpName())
                )
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
