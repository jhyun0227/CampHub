package com.project.camphub.member.service;

import com.project.camphub.login.redis.RedisRepository;
import com.project.camphub.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final RedisRepository redisRepository;

    /**
     * 로그아웃 메서드
     */
    public void logout(Member member) {
        redisRepository.deleteRefreshToken(member.getMbEmail());
    }
}
