package com.project.camphub.member.dto;

import com.project.camphub.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberDto {

    private String mbId;
    private String mbEmail;
    private String mbName;
    private String mbNickname;
    private String mbPicture;

    /**
     * Entity -> Dto
     */
    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .mbId(member.getMbId())
                .mbEmail(member.getMbEmail())
                .mbName(member.getMbName())
                .mbNickname(member.getMbNickname())
                .mbPicture(member.getMbPicture())
                .build();
    }
}
