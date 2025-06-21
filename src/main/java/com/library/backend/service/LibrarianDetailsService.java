package com.library.backend.service;

import com.library.backend.model.Librarian;
import com.library.backend.repository.LibrarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
@RequiredArgsConstructor
public class LibrarianDetailsService implements UserDetailsService {

    private final LibrarianRepository librarianRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Librarian librarian = librarianRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Librarian not found"));

        return new User(
                librarian.getEmail(),
                librarian.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))
        );
    }
}
