package com.project.camphub.camp.entity;

import com.project.camphub.camp.entity.enumcode.CslResult;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CampSyncLog {

    @Id
    private String cslId;
    private LocalDateTime cslTime;

    @Enumerated(EnumType.STRING)
    private CslResult cslResult;

    /**
     * 데이터 동기화 성공
     */
    public static CampSyncLog syncSuccess() {
        return CampSyncLog.builder()
                .cslId(UUID.randomUUID().toString())
                .cslTime(LocalDateTime.now())
                .cslResult(CslResult.SUCCESS)
                .build();
    }

    /**
     * 데이터 동기화 실패
     */
    public static CampSyncLog syncFail() {
        return CampSyncLog.builder()
                .cslId(UUID.randomUUID().toString())
                .cslTime(LocalDateTime.now())
                .cslResult(CslResult.FAIL)
                .build();
    }

}
