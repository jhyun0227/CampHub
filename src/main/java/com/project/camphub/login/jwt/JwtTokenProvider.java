package com.project.camphub.login.jwt;

import com.project.camphub.login.AuthProperties;
import com.project.camphub.login.redis.RedisRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@Transactional(readOnly = true)
public class JwtTokenProvider implements InitializingBean {

    private final String secretKey;
    private static Key signingKey;
    private final Long accessTokenValidityInSeconds;
    private final Long refreshTokenValidityInSeconds;
    private final Long accessTokenValidityInMilliseconds;
    private final Long refreshTokenValidityInMilliseconds;

    private final UserDetailsService userDetailsService;

    private final RedisRepository redisRepository;

    public JwtTokenProvider(
            @Value("${jwt.encoding.secretkey}") String secretKey,
            @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInSeconds,
            UserDetailsService userDetailsService,
            RedisRepository redisRepository) {
        this.secretKey = secretKey;
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
        this.userDetailsService = userDetailsService;
        this.redisRepository = redisRepository;
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
    public TokenDto generateToken(String mbEmail, String authority) {

        //Claims에 들어갈 파라미터
        log.info("mbEmail = {}", mbEmail);
        log.info("authority = {}", authority);

        //오늘날짜 구하기
        long now = System.currentTimeMillis();
        Date today = new Date(now);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = formatter.format(today);

        //쿠키 저장시 띄어쓰기가 에러를 불러일으키기 때문에 접두사는 빼는것으로 결정
        //String accessToken = "Bearer " + createAccessToken(mbEmail, authority, now, todayString);
        String accessToken = createAccessToken(mbEmail, authority, now, todayString);
        String refreshToken = createRefreshToken(mbEmail, now, todayString);

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
                .claim(AuthProperties.MEMBER_EMAIL, mbEmail)
                .claim(AuthProperties.AUTHORITY_KEY, authority)
                .claim("issueDate", todayString)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * RefreshToken 생성
     * redis에 refreshToken 값을 저장한다.
     */
    public String createRefreshToken(String mbEmail, long now, String todayString) {
        //refreshToken 생성
        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .setSubject("refreshToken")
                .claim(AuthProperties.MEMBER_EMAIL, mbEmail)
                .claim("issueDate", todayString)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();

        //redis에 저장
        redisRepository.saveRefreshToken(mbEmail, refreshToken, refreshTokenValidityInSeconds);

        return refreshToken;
    }

    /**
     * JwtAuthentication에서 브라우저를 통해 전달받은 쿠키가 유효한지 체크하는 메서드
     * 에러가 없을 경우, 유효한 토큰으로 간주하고 해당 문자열을 반환한다.
     * 기한의 만료될 경우에 해당 문자열을 간주한다.
     * 그 외의 모든 에러는 유효하지 않은 토큰으로 간주한다.
     */
    public String checkAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(accessToken);

            log.info("checkAccessToken = {}", "유효한 토큰 입니다.");

            return AuthProperties.VALID;

        } catch (ExpiredJwtException e) {

            log.info("checkAccessToken = {}", "기한이 만료된 토큰입니다.");

            return AuthProperties.EXPIRED;

        } catch (Exception e) {
            log.info("checkAccessToken = {}", "유효하지 않은 토큰 입니다. " + e.getMessage());

            return AuthProperties.INVALID;
        }
    }

    /**
     * AccessToken이 만료되었을 경우, RefreshToken의 유효기간을 체크하는 메서드
     * AccessToken과 다르게 RefreshToken은 유효기간이 지나도 유효하지 않은 것으로 간주한다.
     *
     * redis에 존재하는 RefreshToken도 검증한다.
     */
    public String checkRefreshToken(String refreshToken) {
        try {
            //우선 쿠키의 RefreshToken의 복호화를 통해서 유효성을 검증
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            //에러가 발생하지 않을 경우 Redis 내부의 RefreshToken의 값이 있는지 확인하여 유효성을 검증한다.
            String mbEmail = claims.get(AuthProperties.MEMBER_EMAIL).toString();
            String redisRefreshToken = redisRepository.getRefreshToken(mbEmail);

            if (!StringUtils.hasText(redisRefreshToken) || !refreshToken.equals(redisRefreshToken)) {

                //쿠키 RefreshToken과 Redis RefreshToken 값이 다르면 Redis RefreshToken을 삭제한다.
                if (!refreshToken.equals(redisRefreshToken)) {
                    redisRepository.deleteRefreshToken(mbEmail);
                }

                log.info("checkRefreshToken = {}", "유효하지 않은 토큰 입니다.");
                return AuthProperties.INVALID;
            }

            log.info("checkRefreshToken = {}", "유효한 토큰 입니다.");
            return AuthProperties.VALID;

        } catch (Exception e) {

            log.info("checkRefreshToken = {}", "유효하지 않은 토큰 입니다.");
            return AuthProperties.INVALID;
        }
    }

    /**
     * AccessToken에서 Claims을 추출하여 mbEmail(멤버이메일) 값을 얻고,
     * 해당 정보로 계정정보를 조회하여 SecurityContext에 담을 Authentication 객체를 반환하는 메서드
     */
    public Authentication getAuthentication(String accessToken) {
        //토큰에서 Claim을 추출
        Claims claims = this.getClaims(accessToken);
        String mbEmail = claims.get(AuthProperties.MEMBER_EMAIL).toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(mbEmail);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰으로 부터 Claim을 추출하는 메서드
     * accessToken이 만료되어도 자동 refresh를 위해 Claims를 꺼낸다.
     */
    public Claims getClaims(String accessToken) {
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

    /**
     * Response 객체에 Cookie를 셋팅하는 메서드
     * 같은 도메인에서 SSR과 CSR 방식을 둘 다 사용하기 위해 편의성을 위해 토큰을 Header에 저장하지 않고, Cookie에 저장
     * 노션 정리5
     */
    public void setCookieInResponse(HttpServletResponse response, TokenDto tokenDto) {
        response.setContentType("text/html;charset=UTF-8");

        /**
         * Path : 어떤 경로에서 쿠키를 보낼 것인지, "/"일 경우 모든 요청에 쿠키를 전달
         * HttpOnly : 자바스크립트에서 쿠키값을 꺼낼수 없도록 한다. XSS 방지
         * SameSite : 같은 사이트에서의 요청에서만 쿠키가 전송된다. CSRF 방지
         *
         * AccessToken의 쿠키 만료시간은 RefreshToken과 같게한다. (7일)
         * AccessToken의 쿠키의 만료시간을 30분으로 설정할 경우, 30분 이후의 요청에서는 AccessToken이 Request로 전달되지 않기 떄문에 재 로그인이 필요하다.
         * 하지만 AccessToken의 쿠키시간을 RefreshToken과 같게한다면, 토큰 자체의 만료시간이 지나도 브라우저에 남아있게 되고 RefreshToken이 만료되어있지 않을 경우 자동 재발급이 가능하다.
         * 그리고 RefreshToken이 만료될경우 어차피 재로그인을 해야하기 때문에,
         * AccessToken의 자체 만료기간은 30분으로 두지만 쿠키의 만료기간은 7일로 설정한다.
         */
        String accessTokenCookie = AuthProperties.ACCESS + "=" + tokenDto.getAccessToken() + "; Max-Age=" + refreshTokenValidityInSeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", accessTokenCookie);

        String refreshTokenCookie = AuthProperties.REFRESH + "=" + tokenDto.getRefreshToken() + "; Max-Age=" + refreshTokenValidityInSeconds + "; HttpOnly; Path=/; SameSite=Strict";
        response.addHeader("Set-Cookie", refreshTokenCookie);
    }
}
