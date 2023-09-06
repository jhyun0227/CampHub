package com.project.camphub.member.entity;

import com.project.camphub.login.oauth.OAuth2Attribute;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private String mbId;
    private String mbEmail;
    private String mbPassword;
    private String mbName;
    private String mbNickname;
    private String mbPicture;

    @Enumerated(EnumType.STRING)
    private Role mbRole;

    private LocalDateTime mbJoinDate;

    /**
     * OAuth2.0 정보 업데이트
     */
    public void updateOAuth2Attributes(OAuth2Attribute oAuth2Attribute) {
        this.mbName = oAuth2Attribute.getName();
        this.mbPicture = oAuth2Attribute.getPicture();
    }

}
