package com.project.camphub.login.redis;

import com.project.camphub.login.LoginProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * refreshToken을 만료시간을 정하여 저장하는 메서드
     */
    public void saveRefreshToken(String key, String value, Long timeout) {
        try {

            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);

        } catch (Exception e) {

            log.error("RedisRepository.saveRefreshToken 예외 발생", e);

        }
    }

    /**
     * refreshToken을 검증하기 위해 가져오는 메서드
     */
    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * refreshToken을 redis에서 삭제하는 메서드
     * refreshToken가 유효하지 않거나, 사용자가 로그아웃할 경우 사용한다.
     */
    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }
}
