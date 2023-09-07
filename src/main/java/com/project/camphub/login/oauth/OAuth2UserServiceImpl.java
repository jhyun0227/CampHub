package com.project.camphub.login.oauth;

import com.project.camphub.member.Repository.MemberRepository;
import com.project.camphub.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //구글측에서 보내준 정보를 OAuth2User 객체로 변환
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes(); //서버로부터 전달받은 유저정보
        log.info("OAuth2UserServiceImpl.getAttributes() = {}", oAuth2User.getAttributes());
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //요청서버명
        log.info("OAuth2UserServiceImpl.registrationId = {}", registrationId);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); //해당서버의 고유키의 컬럼명
        log.info("OAuth2UserServiceImpl.userNameAttributeName = {}", userNameAttributeName);


        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, attributes);

        Member member = this.memberSaveOrUpdate(registrationId, oAuth2Attribute);

        /**
         * 해당 반환 객체는 SecurityContext에 저장된다.
         * Session 방식을 이용할 경우, Security Context는 HTTP세션에 연결이 된다.
         * Session 방식을 이용하지 않을 경우, 리다이렉션 되는 페이지의 인증과 인가를 넘기기 위한 역할 밖에 되지 않는다.
         */
        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(member.getMbRole().getKey())),
                oAuth2Attribute.getAttributes(),
                oAuth2Attribute.getNameAttributeKey());
    }

    /**
     * 서버로부터 전달받은 정보로, DB에 저장되어있지 않은 이메일이라면 저장, 저장되어 있던 이메일이라면 정보를 업데이트한다.
     */
    private Member memberSaveOrUpdate(String registrationId, OAuth2Attribute oAuth2Attribute) {
        Optional<Member> findMember = memberRepository.findByMbEmail(oAuth2Attribute.getEmail());

        //정보가 없으면 새로 저장
        if (findMember.isEmpty()) {
            log.info("신규 회원, 데이터 저장");

            Member member = oAuth2Attribute.toEntity(registrationId, bCryptPasswordEncoder);
            memberRepository.save(member);

            return member;
        }

        log.info("기존 회원, 데이터 업데이트");

        Member member = findMember.get();
        member.updateOAuth2Attributes(oAuth2Attribute);

        return member;
    }
}
