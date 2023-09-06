package com.project.camphub.login.jwt;

import com.project.camphub.login.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@Transactional(readOnly = true)
public class JwtTokenProvider implements InitializingBean {

    private final String secretKey;
    private static Key signingKey;
    private final Long accessTokenValidityInMilliseconds;
    private final Long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(
            @Value("${jwt.encoding.secretkey}") String secretKey,
            @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInMilliseconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInMilliseconds) {
        this.secretKey = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
    }

    //시크릿 키 설정
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        signingKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    /**
     * TokenDto 생성
     */
    public TokenDto generateToken(OAuth2User oAuth2User) {
        //오늘날짜 구하기
        long now = System.currentTimeMillis();
        Date today = new Date(now);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = formatter.format(today);

        //claims
        String mbEmail = (String) oAuth2User.getAttribute("email");
        log.info("mbEmail = {}", mbEmail);
        String authority = oAuth2User.getAuthorities().iterator().next().getAuthority().substring(5);
        log.info("authority = {}", authority);

        String accessToken = "Bearer " + createAccessToken(mbEmail, authority, now, todayString);
        String refreshToken = createRefreshToken(now, todayString);

        return new TokenDto(accessToken, refreshToken);
    }

    /**
     * AccessToken 생성
     */
    public String createAccessToken(String mbEmail, String authority, long now, String todayString) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                .setSubject("accessToken")
                .claim(SecurityProperties.URL, true)
                .claim(SecurityProperties.MEMBER_EMAIL, mbEmail)
                .claim(SecurityProperties.AUTHORITY_KEY, authority)
                .claim("issueDate", todayString)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(long now, String todayString) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .setSubject("refreshToken")
                .claim("issueDate", todayString)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
