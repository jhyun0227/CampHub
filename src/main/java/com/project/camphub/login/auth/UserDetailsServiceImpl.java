package com.project.camphub.login.auth;

import com.project.camphub.member.Repository.MemberRepository;
import com.project.camphub.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByMbEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Doesn't Exist Member or Withdrawn Member"));

        return new UserDetailsImpl(member);
    }
}
