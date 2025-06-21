package com.library.backend.repository;

import com.library.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<Member> findByEmail(String email);
}