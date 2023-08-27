package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class AreaCodeMap {

    /**
     * 도 코드와 도명을 묶기 위한 클래스
     * 데이터베이스를 이용해서 해당 작업을 해야하는데, 시간적 여유를 위해 임시방편으로 Map을 이용하여 하기로 결정
     */
    private Map<String, String> doMap; //도

    private Map<String, String> suSigunguMap; //서울
    private Map<String, String> bsSigunguMap; //부산
    private Map<String, String> dgSigunguMap; //대구
    private Map<String, String> icSigunguMap; //대전
    private Map<String, String> gjSigunguMap; //광주
    private Map<String, String> djSigunguMap; //대전
    private Map<String, String> usSigunguMap; //울산
    private Map<String, String> sjSigunguMap; //세종
    private Map<String, String> ggSigunguMap; //경기
    private Map<String, String> gwSigunguMap; //강원
    private Map<String, String> cbSigunguMap; //충북
    private Map<String, String> cnSigunguMap; //충남
    private Map<String, String> jbSigunguMap; //전북
    private Map<String, String> jnSigunguMap; //전남
    private Map<String, String> gbSigunguMap; //경북
    private Map<String, String> gnSigunguMap; //경남
    private Map<String, String> jjSigunguMap; //제주
    private Map<String, Map<String, String>> relationMap; //도,시군구매핑

    @PostConstruct
    public void areaCodeSet() {
        setDo();
        setSigungu();
    }

    private void setDo() {
        doMap = new HashMap<>();

        doMap.put("01", "서울시");
        doMap.put("02", "부산시");
        doMap.put("03", "대구시");
        doMap.put("04", "인천시");
        doMap.put("05", "광주시");
        doMap.put("06", "대전시");
        doMap.put("07", "울산시");
        doMap.put("08", "세종시");
        doMap.put("09", "경기도");
        doMap.put("10", "강원도");
        doMap.put("11", "충청북도");
        doMap.put("12", "충청남도");
        doMap.put("13", "전라북도");
        doMap.put("14", "전라남도");
        doMap.put("15", "경상북도");
        doMap.put("16", "경상남도");
        doMap.put("17", "제주도");
    }

    private void setSigungu() {
        relationMap = new HashMap<>();

        //서울시
        suSigunguMap = new HashMap<>();
        suSigunguMap.put("0101", "강남구");
        suSigunguMap.put("0102", "강동구");
        suSigunguMap.put("0103", "강북구");
        suSigunguMap.put("0104", "강서구");
        suSigunguMap.put("0105", "관악구");
        suSigunguMap.put("0106", "광진구");
        suSigunguMap.put("0107", "구로구");
        suSigunguMap.put("0108", "금천구");
        suSigunguMap.put("0109", "노원구");
        suSigunguMap.put("0110", "도봉구");
        suSigunguMap.put("0111", "동대문구");
        suSigunguMap.put("0112", "동작구");
        suSigunguMap.put("0113", "마포구");
        suSigunguMap.put("0114", "서대문구");
        suSigunguMap.put("0115", "서초구");
        suSigunguMap.put("0116", "성동구");
        suSigunguMap.put("0117", "성북구");
        suSigunguMap.put("0118", "송파구");
        suSigunguMap.put("0119", "양천구");
        suSigunguMap.put("0120", "영등포구");
        suSigunguMap.put("0121", "용산구");
        suSigunguMap.put("0122", "은평구");
        suSigunguMap.put("0123", "종로구");
        suSigunguMap.put("0124", "중구");
        suSigunguMap.put("0125", "중랑구");
        relationMap.put("01", suSigunguMap);

        //부산시
        bsSigunguMap = new HashMap<>();
        bsSigunguMap.put("0201", "강서구");
        bsSigunguMap.put("0202", "금정구");
        bsSigunguMap.put("0203", "기장군");
        bsSigunguMap.put("0204", "남구");
        bsSigunguMap.put("0205", "동구");
        bsSigunguMap.put("0206", "동래구");
        bsSigunguMap.put("0207", "부산진구");
        bsSigunguMap.put("0208", "북구");
        bsSigunguMap.put("0209", "사상구");
        bsSigunguMap.put("0210", "사하구");
        bsSigunguMap.put("0211", "서구");
        bsSigunguMap.put("0212", "수영구");
        bsSigunguMap.put("0213", "연제구");
        bsSigunguMap.put("0214", "영도구");
        bsSigunguMap.put("0215", "중구");
        bsSigunguMap.put("0216", "해운대구");
        relationMap.put("02", bsSigunguMap);

        //대구시
        dgSigunguMap = new HashMap<>();
        dgSigunguMap.put("0301", "남구");
        dgSigunguMap.put("0302", "달서구");
        dgSigunguMap.put("0303", "달성군");
        dgSigunguMap.put("0304", "동구");
        dgSigunguMap.put("0305", "북구");
        dgSigunguMap.put("0306", "상주");
        dgSigunguMap.put("0307", "서구");
        dgSigunguMap.put("0308", "수성구");
        dgSigunguMap.put("0309", "중구");
        dgSigunguMap.put("0310", "군위군");
        relationMap.put("03", dgSigunguMap);

        //인천시
        icSigunguMap = new HashMap<>();
        icSigunguMap.put("0401", "강화군");
        icSigunguMap.put("0402", "계양구");
        icSigunguMap.put("0403", "남구");
        icSigunguMap.put("0404", "남동구");
        icSigunguMap.put("0405", "동구");
        icSigunguMap.put("0406", "부평구");
        icSigunguMap.put("0407", "서구");
        icSigunguMap.put("0408", "연수구");
        icSigunguMap.put("0409", "옹진군");
        icSigunguMap.put("0410", "중구");
        relationMap.put("04", icSigunguMap);

        //광주시
        gjSigunguMap = new HashMap<>();
        gjSigunguMap.put("0501", "광산구");
        gjSigunguMap.put("0502", "남구");
        gjSigunguMap.put("0503", "동구");
        gjSigunguMap.put("0504", "북구");
        gjSigunguMap.put("0505", "서구");
        relationMap.put("05", gjSigunguMap);

        //대전시
        djSigunguMap = new HashMap<>();
        djSigunguMap.put("0601", "대덕구");
        djSigunguMap.put("0602", "동구");
        djSigunguMap.put("0603", "서구");
        djSigunguMap.put("0604", "유성구");
        djSigunguMap.put("0605", "중구");
        relationMap.put("06", djSigunguMap);

        //울산시
        usSigunguMap = new HashMap<>();
        usSigunguMap.put("0701", "남구");
        usSigunguMap.put("0702", "동구");
        usSigunguMap.put("0703", "북구");
        usSigunguMap.put("0704", "울주군");
        usSigunguMap.put("0705", "중구");
        relationMap.put("07", gjSigunguMap);

        //세종시
        sjSigunguMap = new HashMap<>();
        sjSigunguMap.put("0801", "금남면");
        sjSigunguMap.put("0802", "세종시");
        sjSigunguMap.put("0803", "소정면");
        sjSigunguMap.put("0804", "연서면");
        sjSigunguMap.put("0805", "전동면");
        relationMap.put("08", sjSigunguMap);

        //경기도
        ggSigunguMap = new HashMap<>();
        ggSigunguMap.put("0901", "가평군");
        ggSigunguMap.put("0902", "고양시");
        ggSigunguMap.put("0903", "과천시");
        ggSigunguMap.put("0904", "광명시");
        ggSigunguMap.put("0905", "광주시");
        ggSigunguMap.put("0906", "구리시");
        ggSigunguMap.put("0907", "군포시");
        ggSigunguMap.put("0908", "김포시");
        ggSigunguMap.put("0909", "남양주시");
        ggSigunguMap.put("0910", "동두천시");
        ggSigunguMap.put("0911", "부천시");
        ggSigunguMap.put("0912", "성남시");
        ggSigunguMap.put("0913", "수원시");
        ggSigunguMap.put("0914", "시흥시");
        ggSigunguMap.put("0915", "안산시");
        ggSigunguMap.put("0916", "안성시");
        ggSigunguMap.put("0917", "안양시");
        ggSigunguMap.put("0918", "양주시");
        ggSigunguMap.put("0919", "양평군");
        ggSigunguMap.put("0920", "여주시");
        ggSigunguMap.put("0921", "연천군");
        ggSigunguMap.put("0922", "오산시");
        ggSigunguMap.put("0923", "용인시");
        ggSigunguMap.put("0924", "의왕시");
        ggSigunguMap.put("0925", "의정부시");
        ggSigunguMap.put("0926", "이천시");
        ggSigunguMap.put("0927", "파주시");
        ggSigunguMap.put("0928", "평택시");
        ggSigunguMap.put("0929", "포천시");
        ggSigunguMap.put("0930", "하남시");
        ggSigunguMap.put("0931", "화성시");
        relationMap.put("09", ggSigunguMap);

        //강원도
        gwSigunguMap = new HashMap<>();
        gwSigunguMap.put("1001", "강릉시");
        gwSigunguMap.put("1002", "고성군");
        gwSigunguMap.put("1003", "동해시");
        gwSigunguMap.put("1004", "삼척시");
        gwSigunguMap.put("1005", "속초시");
        gwSigunguMap.put("1006", "양구군");
        gwSigunguMap.put("1007", "양양군");
        gwSigunguMap.put("1008", "영월군");
        gwSigunguMap.put("1009", "원주시");
        gwSigunguMap.put("1010", "인제군");
        gwSigunguMap.put("1011", "정선군");
        gwSigunguMap.put("1012", "철원군");
        gwSigunguMap.put("1013", "춘천시");
        gwSigunguMap.put("1014", "태백시");
        gwSigunguMap.put("1015", "평창군");
        gwSigunguMap.put("1016", "홍천군");
        gwSigunguMap.put("1017", "화천군");
        gwSigunguMap.put("1018", "횡성군");
        relationMap.put("10", gwSigunguMap);

        //충청북도
        cbSigunguMap = new HashMap<>();
        cbSigunguMap.put("1101", "괴산군");
        cbSigunguMap.put("1102", "단양군");
        cbSigunguMap.put("1103", "보은군");
        cbSigunguMap.put("1104", "영동군");
        cbSigunguMap.put("1105", "옥천군");
        cbSigunguMap.put("1106", "음성군");
        cbSigunguMap.put("1107", "제천시");
        cbSigunguMap.put("1108", "증평군");
        cbSigunguMap.put("1109", "진천군");
        cbSigunguMap.put("1110", "청원군");
        cbSigunguMap.put("1111", "청주시");
        cbSigunguMap.put("1112", "충주시");
        relationMap.put("11", cbSigunguMap);

        //충청남도
        cnSigunguMap = new HashMap<>();
        cnSigunguMap.put("1201", "계룡시");
        cnSigunguMap.put("1202", "공주시");
        cnSigunguMap.put("1203", "금산군");
        cnSigunguMap.put("1204", "논산시");
        cnSigunguMap.put("1205", "당진시");
        cnSigunguMap.put("1206", "보령시");
        cnSigunguMap.put("1207", "부여군");
        cnSigunguMap.put("1208", "서산시");
        cnSigunguMap.put("1209", "서천군");
        cnSigunguMap.put("1210", "아산시");
        cnSigunguMap.put("1211", "예산군");
        cnSigunguMap.put("1212", "천안시");
        cnSigunguMap.put("1213", "청양군");
        cnSigunguMap.put("1214", "태안군");
        cnSigunguMap.put("1215", "홍성군");
        relationMap.put("12", cnSigunguMap);

        //전라북도
        jbSigunguMap = new HashMap<>();
        jbSigunguMap.put("1301", "고창군");
        jbSigunguMap.put("1302", "군산시");
        jbSigunguMap.put("1303", "김제시");
        jbSigunguMap.put("1304", "남원시");
        jbSigunguMap.put("1305", "무주군");
        jbSigunguMap.put("1306", "부안군");
        jbSigunguMap.put("1307", "순창군");
        jbSigunguMap.put("1308", "완주군");
        jbSigunguMap.put("1309", "익산시");
        jbSigunguMap.put("1310", "임실군");
        jbSigunguMap.put("1311", "장수군");
        jbSigunguMap.put("1312", "전주시");
        jbSigunguMap.put("1313", "정읍시");
        jbSigunguMap.put("1314", "진안군");
        relationMap.put("13", jbSigunguMap);

        //전라남도
        jnSigunguMap = new HashMap<>();
        jnSigunguMap.put("1401", "강진군");
        jnSigunguMap.put("1402", "고흥군");
        jnSigunguMap.put("1403", "곡성군");
        jnSigunguMap.put("1404", "광양시");
        jnSigunguMap.put("1405", "구례군");
        jnSigunguMap.put("1406", "나주시");
        jnSigunguMap.put("1407", "담양군");
        jnSigunguMap.put("1408", "목포시");
        jnSigunguMap.put("1409", "무안군");
        jnSigunguMap.put("1410", "보성군");
        jnSigunguMap.put("1411", "순천시");
        jnSigunguMap.put("1412", "신안군");
        jnSigunguMap.put("1413", "여수시");
        jnSigunguMap.put("1414", "영광군");
        jnSigunguMap.put("1415", "영암군");
        jnSigunguMap.put("1416", "완도군");
        jnSigunguMap.put("1417", "장성군");
        jnSigunguMap.put("1418", "장흥군");
        jnSigunguMap.put("1419", "진도군");
        jnSigunguMap.put("1420", "함평군");
        jnSigunguMap.put("1421", "해남군");
        jnSigunguMap.put("1422", "화순군");
        relationMap.put("14", jnSigunguMap);

        //경상북도
        gbSigunguMap = new HashMap<>();
        gbSigunguMap.put("1501", "경산시");
        gbSigunguMap.put("1502", "경주시");
        gbSigunguMap.put("1503", "고령군");
        gbSigunguMap.put("1504", "구미시");
        gbSigunguMap.put("1505", "김천시");
        gbSigunguMap.put("1506", "문경시");
        gbSigunguMap.put("1507", "봉화군");
        gbSigunguMap.put("1508", "상주시");
        gbSigunguMap.put("1509", "성주군");
        gbSigunguMap.put("1510", "안동시");
        gbSigunguMap.put("1511", "영덕군");
        gbSigunguMap.put("1512", "영양군");
        gbSigunguMap.put("1513", "영주시");
        gbSigunguMap.put("1514", "영천시");
        gbSigunguMap.put("1515", "예천군");
        gbSigunguMap.put("1516", "울릉군");
        gbSigunguMap.put("1517", "울진군");
        gbSigunguMap.put("1518", "의성군");
        gbSigunguMap.put("1519", "청도군");
        gbSigunguMap.put("1520", "청송군");
        gbSigunguMap.put("1521", "칠곡군");
        gbSigunguMap.put("1522", "포항시");
        relationMap.put("15", gbSigunguMap);

        //경상남도
        gnSigunguMap = new HashMap<>();
        gnSigunguMap.put("1601", "거제시");
        gnSigunguMap.put("1602", "거창군");
        gnSigunguMap.put("1603", "고성군");
        gnSigunguMap.put("1604", "김해시");
        gnSigunguMap.put("1605", "남해군");
        gnSigunguMap.put("1606", "밀양시");
        gnSigunguMap.put("1607", "사천시");
        gnSigunguMap.put("1608", "산청군");
        gnSigunguMap.put("1609", "양산시");
        gnSigunguMap.put("1610", "의령군");
        gnSigunguMap.put("1611", "진주시");
        gnSigunguMap.put("1612", "창녕군");
        gnSigunguMap.put("1613", "창원시");
        gnSigunguMap.put("1614", "통영시");
        gnSigunguMap.put("1615", "하동군");
        gnSigunguMap.put("1616", "함안군");
        gnSigunguMap.put("1617", "함양군");
        gnSigunguMap.put("1618", "합천군");
        relationMap.put("16", gnSigunguMap);

        //제주도
        jjSigunguMap = new HashMap<>();
        jjSigunguMap.put("1701", "서귀포시");
        jjSigunguMap.put("1702", "제주시");
        relationMap.put("17", jjSigunguMap);
    }
}
