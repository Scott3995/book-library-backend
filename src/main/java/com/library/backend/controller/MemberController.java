package com.library.backend.controller;

import com.library.backend.model.Member;
import com.library.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping
    public List<Member> all() {
        return memberRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (memberRepository.existsByPhone(member.getPhone())) {
            return ResponseEntity.badRequest().body("Phone already exists");
        }
        return ResponseEntity.ok(memberRepository.save(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Member member) {
        return memberRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(member.getFullName());
                    existing.setEmail(member.getEmail());
                    existing.setPhone(member.getPhone());
                    existing.setStatus(member.getStatus());
                    return ResponseEntity.ok(memberRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        return memberRepository.findById(id)
                .map(existing -> {
                    existing.setStatus(Member.Status.INACTIVE);
                    return ResponseEntity.ok(memberRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}