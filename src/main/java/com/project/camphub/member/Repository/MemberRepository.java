package com.project.camphub.member.Repository;

import com.project.camphub.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMbEmail(String email);
}
