package com.project.camphub.login.oauth;

import com.project.camphub.member.entity.Member;
import com.project.camphub.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String name;
    private String picture;

    public static OAuth2Attribute of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }

        return null;
    }

    public static OAuth2Attribute ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .build();
    }

    public Member toEntity(String registrationId, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .mbId(UUID.randomUUID().toString())
                .mbEmail(this.email)
                .mbName(this.name)
                .mbPassword(bCryptPasswordEncoder.encode(registrationId + "_" + this.email + "_" + this.name))
                .mbNickname(registrationId + "_" + UUID.randomUUID().toString().substring(0, 18))
                .mbPicture(this.picture)
                .mbRole(Role.MEMBER)
                .mbJoinDate(LocalDateTime.now())
                .build();
    }
}
