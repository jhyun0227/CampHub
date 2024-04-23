package com.project.camphub.repository.camp.associations;

import com.project.camphub.domain.camp.entity.Camp;
import com.project.camphub.domain.camp.entity.associations.CampAmenity;
import com.project.camphub.domain.camp.entity.code.AmenityCode;
import com.project.camphub.repository.camp.CampRepository;
import com.project.camphub.repository.camp.code.AmenityCodeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class CampAmenityRepositoryTest {

    @Autowired
    private CampRepository campRepository;
    @Autowired
    private AmenityCodeRepository amenityCodeRepository;
    @Autowired
    private CampAmenityRepository campAmenityRepository;
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private EntityManager em;

    @Test
    @Commit
    void test() {
        Camp camp = campRepository.findById("10").get();
        AmenityCode amenityCode = amenityCodeRepository.findById(10L).get();

        //해당하는 데이터는 1개뿐이 없다.
        List<CampAmenity> findCampAmenityList = campAmenityRepository.findByCampAmenityId_CpId(camp.getCpId());
//        List<CampAmenity> findCampAmenityList = camp.getCampAmenityList();

        CampAmenity campAmenity = findCampAmenityList.get(0);

        PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
        log.info("campAmenity = {}", persistenceUnitUtil.isLoaded(campAmenity));
        log.info("camp = {}", persistenceUnitUtil.isLoaded(campAmenity.getCamp()));
        log.info("amenityCode = {}", persistenceUnitUtil.isLoaded(campAmenity.getAmenityCode()));


        log.info("=================== deleteAll 시작 ===================");
        campAmenityRepository.deleteAll(findCampAmenityList);
//        campAmenityRepository.deleteAllInBatch(findCampAmenityList);
        log.info("=================== deleteAll 끝 ===================");

        campAmenityRepository.save(CampAmenity.createCampAmenity(camp, amenityCode));
        log.info("=================== 끝 ===================");
    }

}