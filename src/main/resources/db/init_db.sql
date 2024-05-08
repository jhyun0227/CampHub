### 시도, 시군구 ###
CREATE TABLE `province_code` (
                                 `prov_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                 `prov_cd_nm` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 PRIMARY KEY (`prov_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `district_code` (
                                 `dist_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                 `dist_cd_nm` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 `prov_cd_id` bigint DEFAULT NULL,
                                 PRIMARY KEY (`dist_cd_id`),
                                 KEY `idx_districtCode_provCdId` (`prov_cd_id`),
                                 CONSTRAINT `fk_districtCode_provinceCode_provCdId` FOREIGN KEY (`prov_cd_id`) REFERENCES `province_code` (`prov_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


### 캠프 ###
CREATE TABLE `camp` (
                        `cp_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `cp_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_tel` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_homepage_url` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_resv_url` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_thumb_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_prov_cd_id` bigint DEFAULT NULL,
                        `cp_dist_cd_id` bigint DEFAULT NULL,
                        `cp_zipcode` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_addr` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_addr_detail` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_lon` double DEFAULT NULL,
                        `cp_lat` double DEFAULT NULL,
                        `cp_directions` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_tour_biz_no` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_biz_no` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `cp_permit_dt` datetime(6) DEFAULT NULL,
                        `cp_create_dt` datetime(6) DEFAULT NULL,
                        `cp_mod_dt` datetime(6) DEFAULT NULL,
                        `cp_read_count` int DEFAULT '0',
                        `cp_is_active` enum('Y','N') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        PRIMARY KEY (`cp_id`),
                        KEY `idx_camp_provCdId` (`cp_prov_cd_id`),
                        KEY `idx_camp_distCdId` (`cp_dist_cd_id`),
                        CONSTRAINT `fk_camp_provinceCode_provCdId` FOREIGN KEY (`cp_prov_cd_id`) REFERENCES `province_code` (`prov_cd_id`),
                        CONSTRAINT `fk_camp_districtCode_distCdId` FOREIGN KEY (`cp_dist_cd_id`) REFERENCES `district_code` (`dist_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_detail` (
                               `cp_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                               `cpd_intro` varchar(5000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_line_intro` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_features` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_tooltip` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_oper_days_type` enum('WEEKDAY','WEEKEND','ALWAYS') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_mng_stat_type` enum('OPERATE','CLOSED','SHUTDOWN') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_mng_div_type` enum('DIRECT','ENTRUST') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_mng_org` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_fclt_div_type` enum('PRIVATE','GOVT','PARK','LEISURE','FOREST') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_res_staff_cnt` int DEFAULT NULL,
                               `cpd_off_start_dt` datetime(6) DEFAULT NULL,
                               `cpd_off_end_dt` datetime(6) DEFAULT NULL,
                               `cpd_cult_ev_yn` enum('Y','N') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_cult_ev_nm` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_expr_prgm_yn` enum('Y','N') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_expr_prgm_nm` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_anim_ent_type` enum('CAN','SMALL','CANNOT') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_prvt_crv_yn` enum('Y','N') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_prvt_trl_yn` enum('Y','N') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `cpd_insured_yn` enum('Y','N') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               PRIMARY KEY (`cp_id`),
                               CONSTRAINT `fk_campDetail_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_facility` (
                                 `cp_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `cpf_total_area` int DEFAULT NULL,
                                 `cpf_amnty_etc` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 `cpf_nrby_fclt_etc` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 `cpf_gnrl_site_cnt` int DEFAULT NULL,
                                 `cpf_car_site_cnt` int DEFAULT NULL,
                                 `cpf_glamp_site_cnt` int DEFAULT NULL,
                                 `cpf_crv_site_cnt` int DEFAULT NULL,
                                 `cpf_prvt_crv_site_cnt` int DEFAULT NULL,
                                 `cpf_brazier_type` enum('PRIVATE','PUBLIC','CANNOT') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 `cpf_sink_cnt` int DEFAULT NULL,
                                 `cpf_toilet_cnt` int DEFAULT NULL,
                                 `cpf_swrm_cnt` int DEFAULT NULL,
                                 `cpf_fire_ext_cnt` int DEFAULT NULL,
                                 `cpf_fire_water_cnt` int DEFAULT NULL,
                                 `cpf_fire_sand_cnt` int DEFAULT NULL,
                                 `cpf_fire_sensor_cnt` int DEFAULT NULL,
                                 PRIMARY KEY (`cp_id`),
                                 CONSTRAINT `fk_campFacility_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_site` (
                             `cp_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                             `cps_site_dist` int DEFAULT NULL,
                             `cps_site_size1_cnt` int DEFAULT NULL,
                             `cps_site_size1_width` int DEFAULT NULL,
                             `cps_site_size1_length` int DEFAULT NULL,
                             `cps_site_size2_cnt` int DEFAULT NULL,
                             `cps_site_size2_width` int DEFAULT NULL,
                             `cps_site_size2_length` int DEFAULT NULL,
                             `cps_site_size3_cnt` int DEFAULT NULL,
                             `cps_site_size3_width` int DEFAULT NULL,
                             `cps_site_size3_length` int DEFAULT NULL,
                             `cps_bttm_grass_cnt` int DEFAULT NULL,
                             `cps_bttm_stn_cnt` int DEFAULT NULL,
                             `cps_bttm_tech_cnt` int DEFAULT NULL,
                             `cps_bttm_gravel_cnt` int DEFAULT NULL,
                             `cps_bttm_dirt_cnt` int DEFAULT NULL,
                             PRIMARY KEY (`cp_id`),
                             CONSTRAINT `fk_campSite_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


### 캠프 코드 ###
CREATE TABLE `reservation_code` (
                                    `resv_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                    `resv_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                    PRIMARY KEY (`resv_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `industry_code` (
                                 `indst_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                 `indst_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 PRIMARY KEY (`indst_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `location_code` (
                                 `loct_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                 `loct_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 PRIMARY KEY (`loct_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `theme_code` (
                              `theme_cd_id` bigint NOT NULL AUTO_INCREMENT,
                              `theme_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                              PRIMARY KEY (`theme_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `season_code` (
                               `season_cd_id` bigint NOT NULL AUTO_INCREMENT,
                               `season_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               PRIMARY KEY (`season_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `equipment_code` (
                                  `equip_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                  `equip_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  PRIMARY KEY (`equip_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `amenity_code` (
                                `amnty_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                `amnty_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                PRIMARY KEY (`amnty_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `nearby_facility_code` (
                                        `nrby_fclt_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                        `nrby_fclt_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                        PRIMARY KEY (`nrby_fclt_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `inner_amenity_code` (
                                      `inner_amnty_cd_id` bigint NOT NULL AUTO_INCREMENT,
                                      `inner_amnty_cd_nm` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                      PRIMARY KEY (`inner_amnty_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


### 캠프<->코드 연관관계 ###
CREATE TABLE `camp_reservation` (
                                    `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    `resv_cd_id` bigint NOT NULL,
                                    PRIMARY KEY (`cp_id`, `resv_cd_id`),
                                    KEY `idx_campReservation_cpId` (`cp_id`),
                                    KEY `idx_campReservation_resvCdId` (`resv_cd_id`),
                                    CONSTRAINT `fk_campReservation_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                    CONSTRAINT `fk_campReservation_reservationCode_resvCdId` FOREIGN KEY (`resv_cd_id`) REFERENCES `reservation_code` (`resv_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_industry` (
                                 `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `indst_cd_id` bigint NOT NULL,
                                 PRIMARY KEY (`cp_id`, `indst_cd_id`),
                                 KEY `idx_campIndustry_cpId` (`cp_id`),
                                 KEY `idx_campIndustry_indstCdId` (`indst_cd_id`),
                                 CONSTRAINT `fk_campIndustry_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                 CONSTRAINT `fk_campIndustry_industryCode_indstCdId` FOREIGN KEY (`indst_cd_id`) REFERENCES `industry_code` (`indst_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_location` (
                                 `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `loct_cd_id` bigint NOT NULL,
                                 PRIMARY KEY (`cp_id`, `loct_cd_id`),
                                 KEY `idx_campLocation_cpId` (`cp_id`),
                                 KEY `idx_campLocation_loctCdId` (`loct_cd_id`),
                                 CONSTRAINT `fk_campLocation_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                 CONSTRAINT `fk_campLocation_locationCode_locdCdId` FOREIGN KEY (`loct_cd_id`) REFERENCES `location_code` (`loct_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_theme` (
                              `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                              `theme_cd_id` bigint NOT NULL,
                              PRIMARY KEY (`cp_id`, `theme_cd_id`),
                              KEY `idx_campTheme_cpId` (`cp_id`),
                              KEY `idx_campTheme_themeCdId` (`theme_cd_id`),
                              CONSTRAINT `fk_campTheme_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                              CONSTRAINT `fk_campTheme_themeCode_themeCdId` FOREIGN KEY (`theme_cd_id`) REFERENCES `theme_code` (`theme_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_travel_season` (
                                      `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                      `season_cd_id` bigint NOT NULL,
                                      PRIMARY KEY (`cp_id`, `season_cd_id`),
                                      KEY `idx_campTravelSeason_cpId` (`cp_id`),
                                      KEY `idx_campTravelSeason_seasonCdId` (`season_cd_id`),
                                      CONSTRAINT `fk_campTravelSeason_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                      CONSTRAINT `fk_campTravelSeason_seasonCode_seasonCdId` FOREIGN KEY (`season_cd_id`) REFERENCES `season_code` (`season_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_operation_season` (
                                         `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `season_cd_id` bigint NOT NULL,
                                         PRIMARY KEY (`cp_id`, `season_cd_id`),
                                         KEY `idx_campOperationSeason_cpId` (`cp_id`),
                                         KEY `idx_campOperationSeason_seasonCdId` (`season_cd_id`),
                                         CONSTRAINT `fk_campOperationSeason_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                         CONSTRAINT `fk_campOperationSeason_seasonCode_seasonCdId` FOREIGN KEY (`season_cd_id`) REFERENCES `season_code` (`season_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_equipment_rental` (
                                         `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `equip_cd_id` bigint NOT NULL,
                                         PRIMARY KEY (`cp_id`, `equip_cd_id`),
                                         KEY `idx_campEquipmentRental_cpId` (`cp_id`),
                                         KEY `idx_campEquipmentRental_equipCdId` (`equip_cd_id`),
                                         CONSTRAINT `fk_campEquipmentRental_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                         CONSTRAINT `fk_campEquipmentRental_equipmentCode_equipCdId` FOREIGN KEY (`equip_cd_id`) REFERENCES `equipment_code` (`equip_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_amenity` (
                                `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `amnty_cd_id` bigint NOT NULL,
                                PRIMARY KEY (`cp_id`, `amnty_cd_id`),
                                KEY `idx_campAmenity_cpId` (`cp_id`),
                                KEY `idx_campAmenity_amntyCdId` (`amnty_cd_id`),
                                CONSTRAINT `fk_campAmenity_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                CONSTRAINT `fk_campAmenity_amenityCode_amntyCdId` FOREIGN KEY (`amnty_cd_id`) REFERENCES `amenity_code` (`amnty_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_nearby_facility` (
                                        `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                        `nrby_fclt_cd_id` bigint NOT NULL,
                                        PRIMARY KEY (`cp_id`, `nrby_fclt_cd_id`),
                                        KEY `idx_campNearbyFacility_cpId` (`cp_id`),
                                        KEY `idx_campNearbyFacility_nrbyFcltCdId` (`nrby_fclt_cd_id`),
                                        CONSTRAINT `fk_campNearbyFacility_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                        CONSTRAINT `fk_campNearbyFacility_nearbyFacilityCode_nrbyFcltCdId` FOREIGN KEY (`nrby_fclt_cd_id`) REFERENCES `nearby_facility_code` (`nrby_fclt_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_glamping_inner_amenity` (
                                               `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                               `inner_amnty_cd_id` bigint NOT NULL,
                                               PRIMARY KEY (`cp_id`, `inner_amnty_cd_id`),
                                               KEY `idx_campGlampingInnerAmenity_cpId` (`cp_id`),
                                               KEY `idx_campGlampingInnerAmenity_innerAmntyCdId` (`inner_amnty_cd_id`),
                                               CONSTRAINT `fk_campGlampingInnerAmenity_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                               CONSTRAINT `fk_campGlampingInnerAmenity_innerAmenityCode_innerAmntyCdId` FOREIGN KEY (`inner_amnty_cd_id`) REFERENCES `inner_amenity_code` (`inner_amnty_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `camp_caravan_inner_amenity` (
                                              `cp_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                              `inner_amnty_cd_id` bigint NOT NULL,
                                              PRIMARY KEY (`cp_id`, `inner_amnty_cd_id`),
                                              KEY `idx_campCaravanInnerAmenity_cpId` (`cp_id`),
                                              KEY `idx_campCaravanInnerAmenity_innerAmntyCdId` (`inner_amnty_cd_id`),
                                              CONSTRAINT `fk_campCaravanInnerAmenity_camp_cpId` FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`),
                                              CONSTRAINT `fk_campCaravanInnerAmenity_innerAmenityCode_innerAmntyCdId` FOREIGN KEY (`inner_amnty_cd_id`) REFERENCES `inner_amenity_code` (`inner_amnty_cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;




### 시도
INSERT INTO province_code(prov_cd_nm) VALUES ('서울시');
INSERT INTO province_code(prov_cd_nm) VALUES ('부산시');
INSERT INTO province_code(prov_cd_nm) VALUES ('대구시');
INSERT INTO province_code(prov_cd_nm) VALUES ('인천시');
INSERT INTO province_code(prov_cd_nm) VALUES ('광주시');
INSERT INTO province_code(prov_cd_nm) VALUES ('대전시');
INSERT INTO province_code(prov_cd_nm) VALUES ('울산시');
INSERT INTO province_code(prov_cd_nm) VALUES ('세종시');
INSERT INTO province_code(prov_cd_nm) VALUES ('경기도');
INSERT INTO province_code(prov_cd_nm) VALUES ('강원도');
INSERT INTO province_code(prov_cd_nm) VALUES ('충청북도');
INSERT INTO province_code(prov_cd_nm) VALUES ('충청남도');
INSERT INTO province_code(prov_cd_nm) VALUES ('전라북도');
INSERT INTO province_code(prov_cd_nm) VALUES ('전라남도');
INSERT INTO province_code(prov_cd_nm) VALUES ('경상북도');
INSERT INTO province_code(prov_cd_nm) VALUES ('경상남도');
INSERT INTO province_code(prov_cd_nm) VALUES ('제주도');

### 서울시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강남구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강동구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강북구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강서구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('관악구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('광진구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('구로구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('금천구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('노원구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('도봉구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동대문구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동작구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('마포구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서대문구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서초구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('성동구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('성북구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('송파구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('양천구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영등포구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('용산구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('은평구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('종로구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('중구', 1);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('중랑구', 1);

### 부산시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강서구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('금정구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('기장군', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동래구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('부산진구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('북구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('사상구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('사하구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('수영구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('연제구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영도구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('중구', 2);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('해운대구', 2);

### 대구시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남구', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('달서구', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('달성군', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동구', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('북구', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('상주', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서구', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('수성구', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('중구', 3);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('군위군', 3);

### 인천시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강화군', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('계양구', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남구', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남동구', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동구', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('부평구', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서구', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('연수구', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('옹진군', 4);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('중구', 4);

### 광주시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('광산구', 5);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남구', 5);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동구', 5);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('북구', 5);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서구', 5);

### 대전시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('대덕구', 6);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동구', 6);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서구', 6);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('유성구', 6);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('중구', 6);

### 울산시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남구', 7);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동구', 7);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('북구', 7);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('울주군', 7);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('중구', 7);

### 세종시 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('금남면', 8);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('세종시', 8);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('소정면', 8);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('연서면', 8);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('전동면', 8);

### 경기도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('가평군', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('고양시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('과천시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('광명시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('광주시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('구리시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('군포시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('김포시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남양주시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동두천시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('부천시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('성남시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('수원시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('시흥시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('안산시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('안성시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('안양시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('양주시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('양평군', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('여주시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('연천구', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('오산시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('용인시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('의왕시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('의정부시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('이천시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('파주시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('평택시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('포천시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('하남시', 9);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('화성시', 9);

### 강원도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강릉시', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('고성군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('동해시', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('삼척시', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('속초시', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('양구군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('양양군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영월군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('원주시', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('인제군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('정선군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('철원군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('춘천시', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('태백시', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('평창군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('홍천군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('화천군', 10);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('횡성군', 10);

### 충청북도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('괴산군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('단양군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('보은군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영동군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('옥천군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('음성군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('제천시', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('증평군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('진천군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('청원군', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('청주시', 11);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('충주시', 11);

### 충청남도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('계룡시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('공주시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('금산군', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('논산시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('당진시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('보령시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('부여군', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서산시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서천군', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('아산시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('예산군', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('천안시', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('청양군', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('태안군', 12);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('홍성군', 12);

### 전라북도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('고창군', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('군산시', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('김제시', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남원시', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('무주군', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('부안군', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('순창군', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('완주군', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('익산시', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('임실군', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('장수군', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('전주시', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('정읍시', 13);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('진안군', 13);

### 전라남도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('강진군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('고흥군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('곡성군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('광양시', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('구례군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('나주시', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('담양군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('목포시', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('무안군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('보성군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('순천시', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('신안군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('여수시', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영광군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영암군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('완도군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('장성군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('장흥군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('진도군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('함평군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('해남군', 14);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('화순군', 14);

### 경상북도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('경산시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('경주시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('고령군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('구미시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('김천시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('문경시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('봉화군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('상주시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('성주군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('안동시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영덕군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영양군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영주시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('영천시', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('예천군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('울릉군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('울진군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('의성군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('청도군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('청송군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('칠곡군', 15);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('포항시', 15);

### 경상남도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('거제시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('거창군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('고성군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('김해시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('남해군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('밀양시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('사천시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('산청군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('양산시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('의령군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('진주시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('창녕군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('창원시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('통영시', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('하동군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('함안군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('함양군', 16);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('합천군', 16);

### 제주도 시군구
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('서귀포시', 17);
INSERT INTO district_code(dist_cd_nm, prov_cd_id) VALUE ('제주시', 17);


### 코드
INSERT INTO reservation_code(resv_cd_nm) VALUE ('전화');
INSERT INTO reservation_code(resv_cd_nm) VALUE ('현장');
INSERT INTO reservation_code(resv_cd_nm) VALUE ('온라인예약대기');
INSERT INTO reservation_code(resv_cd_nm) VALUE ('온라인실시간예약');

INSERT INTO industry_code(indst_cd_nm) VALUE ('일반야영장');
INSERT INTO industry_code(indst_cd_nm) VALUE ('자동차야영장');
INSERT INTO industry_code(indst_cd_nm) VALUE ('글램핑');
INSERT INTO industry_code(indst_cd_nm) VALUE ('카라반');

INSERT INTO location_code(loct_cd_nm) VALUE ('해변');
INSERT INTO location_code(loct_cd_nm) VALUE ('섬');
INSERT INTO location_code(loct_cd_nm) VALUE ('산');
INSERT INTO location_code(loct_cd_nm) VALUE ('숲');
INSERT INTO location_code(loct_cd_nm) VALUE ('계곡');
INSERT INTO location_code(loct_cd_nm) VALUE ('강');
INSERT INTO location_code(loct_cd_nm) VALUE ('호수');
INSERT INTO location_code(loct_cd_nm) VALUE ('도심');

INSERT INTO theme_code(theme_cd_nm) VALUE ('일출명소');
INSERT INTO theme_code(theme_cd_nm) VALUE ('일몰명소');
INSERT INTO theme_code(theme_cd_nm) VALUE ('수상레저');
INSERT INTO theme_code(theme_cd_nm) VALUE ('항공레저');
INSERT INTO theme_code(theme_cd_nm) VALUE ('스키');
INSERT INTO theme_code(theme_cd_nm) VALUE ('낚시');
INSERT INTO theme_code(theme_cd_nm) VALUE ('액티비티');
INSERT INTO theme_code(theme_cd_nm) VALUE ('봄꽃여행');
INSERT INTO theme_code(theme_cd_nm) VALUE ('여름물놀이');
INSERT INTO theme_code(theme_cd_nm) VALUE ('가을단풍명소');
INSERT INTO theme_code(theme_cd_nm) VALUE ('겨울눈꽃명소');
INSERT INTO theme_code(theme_cd_nm) VALUE ('걷기길');

INSERT INTO season_code(season_cd_nm) VALUE ('봄');
INSERT INTO season_code(season_cd_nm) VALUE ('여름');
INSERT INTO season_code(season_cd_nm) VALUE ('가을');
INSERT INTO season_code(season_cd_nm) VALUE ('겨울');

INSERT INTO equipment_code(equip_cd_nm) VALUE ('텐트');
INSERT INTO equipment_code(equip_cd_nm) VALUE ('침낭');
INSERT INTO equipment_code(equip_cd_nm) VALUE ('릴선');
INSERT INTO equipment_code(equip_cd_nm) VALUE ('식기');
INSERT INTO equipment_code(equip_cd_nm) VALUE ('화로대');
INSERT INTO equipment_code(equip_cd_nm) VALUE ('난방기구');

INSERT INTO amenity_code(amnty_cd_nm) VALUE ('전기');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('무선인터넷');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('장작판매');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('온수');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('트렘폴린');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('물놀이장');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('놀이터');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('산책로');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('운동장');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('운동시설');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('마트.편의점');
INSERT INTO amenity_code(amnty_cd_nm) VALUE ('덤프스테이션');

INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('산책로');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('수영장');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('해수욕');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('계곡 물놀이');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('강/물놀이');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('수상레저');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('낚시');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('어린이놀이시설');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('청소년체험시설');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('농어촌체험시설');
INSERT INTO nearby_facility_code(nrby_fclt_cd_nm) VALUE ('운동장');

INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('TV');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('침대');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('유무선인터넷');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('에어컨');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('냉장고');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('난방기구');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('취사도구');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('내부화장실');
INSERT INTO inner_amenity_code(inner_amnty_cd_nm) VALUE ('내부샤워실');