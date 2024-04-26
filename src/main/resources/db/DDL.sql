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