package com.project.camphub.login.jwt;

import com.project.camphub.login.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(
            @Value("${jwt.encoding.secretkey}") String secretKey,
            @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInMilliseconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInMilliseconds,
            UserDetailsService userDetailsService) {
        this.secretKey = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
        this.userDetailsService = userDetailsService;
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

        //쿠키 저장시 띄어쓰기가 에러를 불러일으키기 때문에 접두사는 빼는것으로 결정
        //String accessToken = "Bearer " + createAccessToken(mbEmail, authority, now, todayString);
        String accessToken = createAccessToken(mbEmail, authority, now, todayString);
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

    /**
     * JwtAuthentication에서 브라우저를 통해 전달받은 쿠키가 유효한지 체크하는 메서드
     * 에러가 발생하지 않으면 유효한 토큰
     * 기한의 만료 토큰일 경우에는 리프레시를 위해 true를 반환한다.
     */
    public boolean checkAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(accessToken);

            log.info("checkAccessToken = {}", "유효한 토큰 입니다.");

        } catch (ExpiredJwtException e) {

            log.info("checkAccessToken = {}", "기한이 만료된 토큰입니다.");

        } catch (Exception e) {
            log.info("checkAccessToken = {}", "유효하지 않은 토큰 입니다. " + e.getMessage());

            return false;
        }

        return true;
    }

    /**
     * AccessToken에서 Claims을 추출하여 mbEmail(멤버이메일) 값을 얻고,
     * 해당 정보로 계정정보를 조회하여 SecurityContext에 담을 Authentication 객체를 반환하는 메서드
     */
    public Authentication getAuthentication(String accessToken) {
        //토큰에서 Claim을 추출
        Claims claims = this.getClaims(accessToken);
        String mbEmail = claims.get(SecurityProperties.MEMBER_EMAIL).toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(mbEmail);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰으로 부터 Claim을 추출하는 메서드
     * accessToken이 만료되어도 자동 refresh를 위해 Claims를 꺼낸다.
     */
    private Claims getClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
