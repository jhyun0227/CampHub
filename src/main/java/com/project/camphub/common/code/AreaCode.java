package com.project.camphub.common.code;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class AreaCode {

    /**
     * 도 코드와 도명을 묶기 위한 클래스
     * 데이터베이스를 이용해서 해당 작업을 해야하는데, 시간적 여유를 위해 임시방편으로 Map을 이용하여 하기로 결정
     */
    private Map<String, String> doCodeMap; //도

    private Map<String, String> suSigunguCodeMap; //서울
    private Map<String, String> bsSigunguCodeMap; //부산
    private Map<String, String> dgSigunguCodeMap; //대구
    private Map<String, String> icSigunguCodeMap; //대전
    private Map<String, String> gjSigunguCodeMap; //광주
    private Map<String, String> djSigunguCodeMap; //대전
    private Map<String, String> usSigunguCodeMap; //울산
    private Map<String, String> sjSigunguCodeMap; //세종
    private Map<String, String> ggSigunguCodeMap; //경기
    private Map<String, String> gwSigunguCodeMap; //강원
    private Map<String, String> cbSigunguCodeMap; //충북
    private Map<String, String> cnSigunguCodeMap; //충남
    private Map<String, String> jbSigunguCodeMap; //전북
    private Map<String, String> jnSigunguCodeMap; //전남
    private Map<String, String> gbSigunguCodeMap; //경북
    private Map<String, String> gnSigunguCodeMap; //경남
    private Map<String, String> jjSigunguCodeMap; //제주
    private Map<String, Map<String, String>> relationMap; //도,시군구매핑

    @PostConstruct
    public void areaCodeSet() {
        setDo();
        setSigungu();
    }

    private void setDo() {
        doCodeMap = new HashMap<>();

        doCodeMap.put("01", "서울시");
        doCodeMap.put("02", "부산시");
        doCodeMap.put("03", "대구시");
        doCodeMap.put("04", "인천시");
        doCodeMap.put("05", "광주시");
        doCodeMap.put("06", "대전시");
        doCodeMap.put("07", "울산시");
        doCodeMap.put("08", "세종시");
        doCodeMap.put("09", "경기도");
        doCodeMap.put("10", "강원도");
        doCodeMap.put("11", "충청북도");
        doCodeMap.put("12", "충청남도");
        doCodeMap.put("13", "전라북도");
        doCodeMap.put("14", "전라남도");
        doCodeMap.put("15", "경상북도");
        doCodeMap.put("16", "경상남도");
        doCodeMap.put("17", "제주도");
    }

    private void setSigungu() {
        relationMap = new HashMap<>();

        //서울시
        suSigunguCodeMap = new HashMap<>();
        suSigunguCodeMap.put("0101", "강남구");
        suSigunguCodeMap.put("0102", "강동구");
        suSigunguCodeMap.put("0103", "강북구");
        suSigunguCodeMap.put("0104", "강서구");
        suSigunguCodeMap.put("0105", "관악구");
        suSigunguCodeMap.put("0106", "광진구");
        suSigunguCodeMap.put("0107", "구로구");
        suSigunguCodeMap.put("0108", "금천구");
        suSigunguCodeMap.put("0109", "노원구");
        suSigunguCodeMap.put("0110", "도봉구");
        suSigunguCodeMap.put("0111", "동대문구");
        suSigunguCodeMap.put("0112", "동작구");
        suSigunguCodeMap.put("0113", "마포구");
        suSigunguCodeMap.put("0114", "서대문구");
        suSigunguCodeMap.put("0115", "서초구");
        suSigunguCodeMap.put("0116", "성동구");
        suSigunguCodeMap.put("0117", "성북구");
        suSigunguCodeMap.put("0118", "송파구");
        suSigunguCodeMap.put("0119", "양천구");
        suSigunguCodeMap.put("0120", "영등포구");
        suSigunguCodeMap.put("0121", "용산구");
        suSigunguCodeMap.put("0122", "은평구");
        suSigunguCodeMap.put("0123", "종로구");
        suSigunguCodeMap.put("0124", "중구");
        suSigunguCodeMap.put("0125", "중랑구");
        relationMap.put("01", suSigunguCodeMap);

        //부산시
        bsSigunguCodeMap = new HashMap<>();
        bsSigunguCodeMap.put("0201", "강서구");
        bsSigunguCodeMap.put("0202", "금정구");
        bsSigunguCodeMap.put("0203", "기장군");
        bsSigunguCodeMap.put("0204", "남구");
        bsSigunguCodeMap.put("0205", "동구");
        bsSigunguCodeMap.put("0206", "동래구");
        bsSigunguCodeMap.put("0207", "부산진구");
        bsSigunguCodeMap.put("0208", "북구");
        bsSigunguCodeMap.put("0209", "사상구");
        bsSigunguCodeMap.put("0210", "사하구");
        bsSigunguCodeMap.put("0211", "서구");
        bsSigunguCodeMap.put("0212", "수영구");
        bsSigunguCodeMap.put("0213", "연제구");
        bsSigunguCodeMap.put("0214", "영도구");
        bsSigunguCodeMap.put("0215", "중구");
        bsSigunguCodeMap.put("0216", "해운대구");
        relationMap.put("02", bsSigunguCodeMap);

        //대구시
        dgSigunguCodeMap = new HashMap<>();
        dgSigunguCodeMap.put("0301", "남구");
        dgSigunguCodeMap.put("0302", "달서구");
        dgSigunguCodeMap.put("0303", "달성군");
        dgSigunguCodeMap.put("0304", "동구");
        dgSigunguCodeMap.put("0305", "북구");
        dgSigunguCodeMap.put("0306", "상주");
        dgSigunguCodeMap.put("0307", "서구");
        dgSigunguCodeMap.put("0308", "수성구");
        dgSigunguCodeMap.put("0309", "중구");
        dgSigunguCodeMap.put("0310", "군위군");
        relationMap.put("03", dgSigunguCodeMap);

        //인천시
        icSigunguCodeMap = new HashMap<>();
        icSigunguCodeMap.put("0401", "강화군");
        icSigunguCodeMap.put("0402", "계양구");
        icSigunguCodeMap.put("0403", "남구");
        icSigunguCodeMap.put("0404", "남동구");
        icSigunguCodeMap.put("0405", "동구");
        icSigunguCodeMap.put("0406", "부평구");
        icSigunguCodeMap.put("0407", "서구");
        icSigunguCodeMap.put("0408", "연수구");
        icSigunguCodeMap.put("0409", "옹진군");
        icSigunguCodeMap.put("0410", "중구");
        relationMap.put("04", icSigunguCodeMap);

        //광주시
        gjSigunguCodeMap = new HashMap<>();
        gjSigunguCodeMap.put("0501", "광산구");
        gjSigunguCodeMap.put("0502", "남구");
        gjSigunguCodeMap.put("0503", "동구");
        gjSigunguCodeMap.put("0504", "북구");
        gjSigunguCodeMap.put("0505", "서구");
        relationMap.put("05", gjSigunguCodeMap);

        //대전시
        djSigunguCodeMap = new HashMap<>();
        djSigunguCodeMap.put("0601", "대덕구");
        djSigunguCodeMap.put("0602", "동구");
        djSigunguCodeMap.put("0603", "서구");
        djSigunguCodeMap.put("0604", "유성구");
        djSigunguCodeMap.put("0605", "중구");
        relationMap.put("06", djSigunguCodeMap);

        //울산시
        usSigunguCodeMap = new HashMap<>();
        usSigunguCodeMap.put("0701", "남구");
        usSigunguCodeMap.put("0702", "동구");
        usSigunguCodeMap.put("0703", "북구");
        usSigunguCodeMap.put("0704", "울주군");
        usSigunguCodeMap.put("0705", "중구");
        relationMap.put("07", gjSigunguCodeMap);

        //세종시
        sjSigunguCodeMap = new HashMap<>();
        sjSigunguCodeMap.put("0801", "금남면");
        sjSigunguCodeMap.put("0802", "세종시");
        sjSigunguCodeMap.put("0803", "소정면");
        sjSigunguCodeMap.put("0804", "연서면");
        sjSigunguCodeMap.put("0805", "전동면");
        relationMap.put("08", sjSigunguCodeMap);

        //경기도
        ggSigunguCodeMap = new HashMap<>();
        ggSigunguCodeMap.put("0901", "가평군");
        ggSigunguCodeMap.put("0902", "고양시");
        ggSigunguCodeMap.put("0903", "과천시");
        ggSigunguCodeMap.put("0904", "광명시");
        ggSigunguCodeMap.put("0905", "광주시");
        ggSigunguCodeMap.put("0906", "구리시");
        ggSigunguCodeMap.put("0907", "군포시");
        ggSigunguCodeMap.put("0908", "김포시");
        ggSigunguCodeMap.put("0909", "남양주시");
        ggSigunguCodeMap.put("0910", "동두천시");
        ggSigunguCodeMap.put("0911", "부천시");
        ggSigunguCodeMap.put("0912", "성남시");
        ggSigunguCodeMap.put("0913", "수원시");
        ggSigunguCodeMap.put("0914", "시흥시");
        ggSigunguCodeMap.put("0915", "안산시");
        ggSigunguCodeMap.put("0916", "안성시");
        ggSigunguCodeMap.put("0917", "안양시");
        ggSigunguCodeMap.put("0918", "양주시");
        ggSigunguCodeMap.put("0919", "양평군");
        ggSigunguCodeMap.put("0920", "여주시");
        ggSigunguCodeMap.put("0921", "연천군");
        ggSigunguCodeMap.put("0922", "오산시");
        ggSigunguCodeMap.put("0923", "용인시");
        ggSigunguCodeMap.put("0924", "의왕시");
        ggSigunguCodeMap.put("0925", "의정부시");
        ggSigunguCodeMap.put("0926", "이천시");
        ggSigunguCodeMap.put("0927", "파주시");
        ggSigunguCodeMap.put("0928", "평택시");
        ggSigunguCodeMap.put("0929", "포천시");
        ggSigunguCodeMap.put("0930", "하남시");
        ggSigunguCodeMap.put("0931", "화성시");
        relationMap.put("09", ggSigunguCodeMap);

        //강원도
        gwSigunguCodeMap = new HashMap<>();
        gwSigunguCodeMap.put("1001", "강릉시");
        gwSigunguCodeMap.put("1002", "고성군");
        gwSigunguCodeMap.put("1003", "동해시");
        gwSigunguCodeMap.put("1004", "삼척시");
        gwSigunguCodeMap.put("1005", "속초시");
        gwSigunguCodeMap.put("1006", "양구군");
        gwSigunguCodeMap.put("1007", "양양군");
        gwSigunguCodeMap.put("1008", "영월군");
        gwSigunguCodeMap.put("1009", "원주시");
        gwSigunguCodeMap.put("1010", "인제군");
        gwSigunguCodeMap.put("1011", "정선군");
        gwSigunguCodeMap.put("1012", "철원군");
        gwSigunguCodeMap.put("1013", "춘천시");
        gwSigunguCodeMap.put("1014", "태백시");
        gwSigunguCodeMap.put("1015", "평창군");
        gwSigunguCodeMap.put("1016", "홍천군");
        gwSigunguCodeMap.put("1017", "화천군");
        gwSigunguCodeMap.put("1018", "횡성군");
        relationMap.put("10", gwSigunguCodeMap);

        //충청북도
        cbSigunguCodeMap = new HashMap<>();
        cbSigunguCodeMap.put("1101", "괴산군");
        cbSigunguCodeMap.put("1102", "단양군");
        cbSigunguCodeMap.put("1103", "보은군");
        cbSigunguCodeMap.put("1104", "영동군");
        cbSigunguCodeMap.put("1105", "옥천군");
        cbSigunguCodeMap.put("1106", "음성군");
        cbSigunguCodeMap.put("1107", "제천시");
        cbSigunguCodeMap.put("1108", "증평군");
        cbSigunguCodeMap.put("1109", "진천군");
        cbSigunguCodeMap.put("1110", "청원군");
        cbSigunguCodeMap.put("1111", "청주시");
        cbSigunguCodeMap.put("1112", "충주시");
        relationMap.put("11", cbSigunguCodeMap);

        //충청남도
        cnSigunguCodeMap = new HashMap<>();
        cnSigunguCodeMap.put("1201", "계룡시");
        cnSigunguCodeMap.put("1202", "공주시");
        cnSigunguCodeMap.put("1203", "금산군");
        cnSigunguCodeMap.put("1204", "논산시");
        cnSigunguCodeMap.put("1205", "당진시");
        cnSigunguCodeMap.put("1206", "보령시");
        cnSigunguCodeMap.put("1207", "부여군");
        cnSigunguCodeMap.put("1208", "서산시");
        cnSigunguCodeMap.put("1209", "서천군");
        cnSigunguCodeMap.put("1210", "아산시");
        cnSigunguCodeMap.put("1211", "예산군");
        cnSigunguCodeMap.put("1212", "천안시");
        cnSigunguCodeMap.put("1213", "청양군");
        cnSigunguCodeMap.put("1214", "태안군");
        cnSigunguCodeMap.put("1215", "홍성군");
        relationMap.put("12", cnSigunguCodeMap);

        //전라북도
        jbSigunguCodeMap = new HashMap<>();
        jbSigunguCodeMap.put("1301", "고창군");
        jbSigunguCodeMap.put("1302", "군산시");
        jbSigunguCodeMap.put("1303", "김제시");
        jbSigunguCodeMap.put("1304", "남원시");
        jbSigunguCodeMap.put("1305", "무주군");
        jbSigunguCodeMap.put("1306", "부안군");
        jbSigunguCodeMap.put("1307", "순창군");
        jbSigunguCodeMap.put("1308", "완주군");
        jbSigunguCodeMap.put("1309", "익산시");
        jbSigunguCodeMap.put("1310", "임실군");
        jbSigunguCodeMap.put("1311", "장수군");
        jbSigunguCodeMap.put("1312", "전주시");
        jbSigunguCodeMap.put("1313", "정읍시");
        jbSigunguCodeMap.put("1314", "진안군");
        relationMap.put("13", jbSigunguCodeMap);

        //전라남도
        jnSigunguCodeMap = new HashMap<>();
        jnSigunguCodeMap.put("1401", "강진군");
        jnSigunguCodeMap.put("1402", "고흥군");
        jnSigunguCodeMap.put("1403", "곡성군");
        jnSigunguCodeMap.put("1404", "광양시");
        jnSigunguCodeMap.put("1405", "구례군");
        jnSigunguCodeMap.put("1406", "나주시");
        jnSigunguCodeMap.put("1407", "담양군");
        jnSigunguCodeMap.put("1408", "목포시");
        jnSigunguCodeMap.put("1409", "무안군");
        jnSigunguCodeMap.put("1410", "보성군");
        jnSigunguCodeMap.put("1411", "순천시");
        jnSigunguCodeMap.put("1412", "신안군");
        jnSigunguCodeMap.put("1413", "여수시");
        jnSigunguCodeMap.put("1414", "영광군");
        jnSigunguCodeMap.put("1415", "영암군");
        jnSigunguCodeMap.put("1416", "완도군");
        jnSigunguCodeMap.put("1417", "장성군");
        jnSigunguCodeMap.put("1418", "장흥군");
        jnSigunguCodeMap.put("1419", "진도군");
        jnSigunguCodeMap.put("1420", "함평군");
        jnSigunguCodeMap.put("1421", "해남군");
        jnSigunguCodeMap.put("1422", "화순군");
        relationMap.put("14", jnSigunguCodeMap);

        //경상북도
        gbSigunguCodeMap = new HashMap<>();
        gbSigunguCodeMap.put("1501", "경산시");
        gbSigunguCodeMap.put("1502", "경주시");
        gbSigunguCodeMap.put("1503", "고령군");
        gbSigunguCodeMap.put("1504", "구미시");
        gbSigunguCodeMap.put("1505", "김천시");
        gbSigunguCodeMap.put("1506", "문경시");
        gbSigunguCodeMap.put("1507", "봉화군");
        gbSigunguCodeMap.put("1508", "상주시");
        gbSigunguCodeMap.put("1509", "성주군");
        gbSigunguCodeMap.put("1510", "안동시");
        gbSigunguCodeMap.put("1511", "영덕군");
        gbSigunguCodeMap.put("1512", "영양군");
        gbSigunguCodeMap.put("1513", "영주시");
        gbSigunguCodeMap.put("1514", "영천시");
        gbSigunguCodeMap.put("1515", "예천군");
        gbSigunguCodeMap.put("1516", "울릉군");
        gbSigunguCodeMap.put("1517", "울진군");
        gbSigunguCodeMap.put("1518", "의성군");
        gbSigunguCodeMap.put("1519", "청도군");
        gbSigunguCodeMap.put("1520", "청송군");
        gbSigunguCodeMap.put("1521", "칠곡군");
        gbSigunguCodeMap.put("1522", "포항시");
        relationMap.put("15", gbSigunguCodeMap);

        //경상남도
        gnSigunguCodeMap = new HashMap<>();
        gnSigunguCodeMap.put("1601", "거제시");
        gnSigunguCodeMap.put("1602", "거창군");
        gnSigunguCodeMap.put("1603", "고성군");
        gnSigunguCodeMap.put("1604", "김해시");
        gnSigunguCodeMap.put("1605", "남해군");
        gnSigunguCodeMap.put("1606", "밀양시");
        gnSigunguCodeMap.put("1607", "사천시");
        gnSigunguCodeMap.put("1608", "산청군");
        gnSigunguCodeMap.put("1609", "양산시");
        gnSigunguCodeMap.put("1610", "의령군");
        gnSigunguCodeMap.put("1611", "진주시");
        gnSigunguCodeMap.put("1612", "창녕군");
        gnSigunguCodeMap.put("1613", "창원시");
        gnSigunguCodeMap.put("1614", "통영시");
        gnSigunguCodeMap.put("1615", "하동군");
        gnSigunguCodeMap.put("1616", "함안군");
        gnSigunguCodeMap.put("1617", "함양군");
        gnSigunguCodeMap.put("1618", "합천군");
        relationMap.put("16", gnSigunguCodeMap);

        //제주도
        jjSigunguCodeMap = new HashMap<>();
        jjSigunguCodeMap.put("1701", "서귀포시");
        jjSigunguCodeMap.put("1702", "제주시");
        relationMap.put("17", jjSigunguCodeMap);
    }
}
