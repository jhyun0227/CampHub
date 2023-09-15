package com.project.camphub.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    private final String redisHost;
    private final int redisPort;

    public RedisConfig(
            @Value("${spring.redis.host}") String redisHost,
            @Value("${spring.redis.port}") int redisPort
    ) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }

    /**
     * Redis는 Lettuce와 Jedis 라이브러리를 사용한다.
     * 여기서는 Lettuce를 사용
     * Redis에 접근하는 방법은 RedisTemplate, RedisRopository 2개
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    /**
     * Redis-cli를 사용하기 위한 설정
     * setKeySerializer, setValueSerializer 설정해주는 이유는
     * RedisTemplate를 사용할 때 Spring - Redis 간 데이터 직렬화, 역직렬화 시 사용하는 방식이 Jdk 직렬화 방식이기 때문
     * 동작에는 문제가 없지만 redis-cli을 통해 직접 데이터를 보려고 할 때 알아볼 수 없는 형태로 출력되기 때문에 적용한 설정
     *
     * java Object를 redis에 저장하는 경우 사용
     */
    @Bean
    public RedisTemplate<?,?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
