package com.project.camphub.domain.camp.registry;

import com.project.camphub.domain.camp.entity.code.IndustryCode;
import com.project.camphub.repository.camp.code.IndustryCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class IndustryMapRegistry {

    private final IndustryCodeRepository industryCodeRepository;

    private final Map<Long, IndustryCode> indstCdMap = new HashMap<>();
//    private final Map<String, IndustryCode> nameToIndstCdMap = new HashMap<>();

    @PostConstruct
    private void init() {
        log.info("IndustryMapRegistry.init() 실행");

        List<IndustryCode> indstCdList = industryCodeRepository.findAll();

        setIndustryCodeMaps(indstCdList);

        log.info("indstCdMap.size()={}", indstCdMap.size());
//        log.info("nameToIndstCdMap.size()={}", nameToIndstCdMap.size());
        log.info("IndustryMapRegistry.init() 종료");
    }

    private void setIndustryCodeMaps(List<IndustryCode> indstCdList) {
        indstCdList.forEach(indstCd -> {
            indstCdMap.put(indstCd.getIndstCdId(), indstCd);
//            nameToIndstCdMap.put(indstCd.getIndstCdNm(), indstCd);
        });
    }

    public IndustryCode findByIndstCdId(Long indstCdId) {
        return indstCdMap.get(indstCdId);
    }

    /*
    public IndustryCode findByIndstCdNm(String indstCdNm) {
        return nameToIndstCdMap.get(indstCdNm);
    }

    public void addIndustryCodeMaps(IndustryCode industryCode) {
        indstCdMap.put(industryCode.getIndstCdId(), industryCode);
        nameToIndstCdMap.put(industryCode.getIndstCdNm(), industryCode);
    }
    */

    public List<String> getIndstCdNmListByIds(List<Long> indstCdIdList) {
        return indstCdIdList.stream()
                .map(indstCdId -> indstCdMap.get(indstCdId).getIndstCdNm())
                .collect(Collectors.toList());
    }
}
