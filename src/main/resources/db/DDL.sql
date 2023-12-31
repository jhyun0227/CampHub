CREATE TABLE `member` (
      mb_id varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '멤버ID',
      mb_email varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '멤버이메일',
      mb_password varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '멤버비밀번호',
      mb_name varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '멤버이름',
      mb_nickname varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '멤버닉네임',
      mb_picture varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '멤버프로필사진',
      mb_role varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '멤버권한',
      mb_join_date datetime COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '멤버가입일자',
      PRIMARY KEY (mb_id),
      CONSTRAINT mb_email_UQ UNIQUE (`mb_email`),
      CONSTRAINT mb_nickname_UQ UNIQUE (`mb_nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='멤버';


CREATE TABLE `camp` (
    `cp_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '캠프ID',
    `cp_content_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '콘텐츠ID',
    `cp_faclt_nm` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '야영장명',
    `cp_tel` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '전화번호',
    `cp_homepage` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '홈페이지',
    `cp_resve_cl` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '예약구분',
    `cp_resve_url` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '예약페이지URL',
    `cp_oper_pd_cl` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '운영기간',
    `cp_oper_de_cl` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '운영일',
    `cp_hvof_bgnde` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '휴장기간, 휴무기간 시작일',
    `cp_hvof_enddle` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '휴장기간, 휴무기간 종료일',
    `cp_induty` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '업종',
    `cp_lct_cl` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '입지구분',
    `cp_thema_envrn_cl` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '테마환경',
    `cp_tour_era_cl` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '여행시기',
    `cp_first_image_url` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '대표이미지',
    `cp_manage_sttus` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '운영상태, 관리상태',
    `cp_manage_div_nm` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '운영주체, 관리주체',
    `cp_mgc_div` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '운영기관, 관리기관',
    `cp_faclt_div_nm` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사업주체, 구분',
    `cp_insrnc_at` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '영업배상책임보험 가입여부',
    `cp_trsagnt_no` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '관광사업자번호',
    `cp_bizrno` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사업자번호',
    `cp_prmisn_de` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '인허가일자',
    `cp_createdtime` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '등록일',
    `cp_modifiedtime` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '수정일',
    `cp_sync_status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '콘텐츠상태',
    PRIMARY KEY (`cp_id`),
    CONSTRAINT cp_content_id_UQ UNIQUE (`cp_content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '캠프';



CREATE TABLE `camp_detail` (
    `cp_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '캠프ID',
    `cpd_intro` varchar(5000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '소개',
    `cpd_line_intro` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '한줄소개',
    `cpd_feature_nm` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '특징',
    `cpd_tooltip` varchar(1500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '툴팁',
    `cpd_direction` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '오시는 길 컨텐츠',
    `cpd_do_nm` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '도',
    `cpd_sigungu_nm` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '시군구',
    `cpd_zipcode` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '우편번호',
    `cpd_addr1` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주소',
    `cpd_addr2` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주소상세',
    `cpd_mapx` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '경도(X)',
    `cpd_mapy` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '위도(Y)',
    `cpd_cltur_event_at` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '자체문화행사 여부',
    `cpd_cltur_event` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '자체문화행사명',
    `cpd_exprn_progrm_at` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '체험프로그램 여부',
    `cpd_exprn_progrm` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '체험프로그램명',
    `cpd_eqpmn_lend_cl` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '캠핑장비대여',
    `cpd_animal_cmg_cl` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '애완동물출입',
    `cpd_carav_acmpny_at` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '개인 카라반 동반 여부',
    `cpd_trler_acmpny_at` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '개인 트레일러 동반 여부',
    PRIMARY KEY (`cp_id`),
    CONSTRAINT cpd_cp_id_FK FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '캠프상세';



CREATE TABLE `camp_facility` (
    `cp_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '캠프ID',
    `cpf_manage_nmpr` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '상주관리인원',
    `cpf_allar` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '전체면적',
    `cpf_sbrs_cl` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '부대시설',
    `cpf_sbrs_etc` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '부대시설 기타',
    `cpf_posbl_fclty_cl` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주변이용가능시설',
    `cpf_posbl_fclty_etc` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주변이용가능시설 기타',
    `cpf_gnrl_site_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주요시설 일반야영장 개수',
    `cpf_auto_site_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주요시설 자동차야영장 개수',
    `cpf_glamp_site_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주요시설 글램핑 개수',
    `cpf_carav_site_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주요시설 카라반 개수',
    `cpf_indvdl_carav_site_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '주요시설 개인 카라반 개수',
    `cpf_glamp_inner_fclty` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '글램핑 - 내부시설',
    `cpf_carav_inner_fclty` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '카라반 - 내부시설',
    `cpf_brazier_cl` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '화로대',
    `cpf_wtrpl_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '개수대 개수',
    `cpf_toilet_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '화장실 개수',
    `cpf_swrm_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '샤워실 개수',
    `cpf_extshr_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '소화기 개수',
    `cpf_frprvt_wrpp_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '방화수 개수',
    `cpf_frprvt_sand_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '방화사 개수',
    `cpf_fire_sensor_co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '화재감시기 개수',
    PRIMARY KEY (`cp_id`),
    CONSTRAINT cpf_cp_id_FK FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '캠프시설';



CREATE TABLE `camp_site` (
    `cp_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '캠프ID',
    `cps_sited_stnc` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트간 거리',
    `cps_site_mg1co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기1 수량',
    `cps_site_mg2co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기2 수량',
    `cps_site_mg3co` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기3 수량',
    `cps_site_mg1width` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기1 가로',
    `cps_site_mg1vrticl` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기1 세로',
    `cps_site_mg2width` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기2 가로',
    `cps_site_mg2vrticl` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기2 세로',
    `cps_site_mg3width` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기3 가로',
    `cps_site_mg3vrticl` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 크기3 세로',
    `cps_site_bottom_cl1` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 바닥 잔디',
    `cps_site_bottom_cl2` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 바닥 파쇄석',
    `cps_site_bottom_cl3` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 바닥 테크',
    `cps_site_bottom_cl4` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 바닥 자갈',
    `cps_site_bottom_cl5` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이트 바닥 맨흙',
    PRIMARY KEY (`cp_id`),
    CONSTRAINT cps_cp_id_FK FOREIGN KEY (`cp_id`) REFERENCES `camp` (`cp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '캠프사이트';


CREATE TABLE `camp_sync_log` (
    `csl_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '캠프동기화로그ID',
    `csl_time` datetime COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '캠프동기화시간',
    `csl_result` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '캠프동기화결과',
    PRIMARY KEY (`csl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '캠프동기화로그';