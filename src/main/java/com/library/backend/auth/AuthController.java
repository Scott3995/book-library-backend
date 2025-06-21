package com.library.backend.auth;

import com.library.backend.model.Librarian;
import com.library.backend.repository.LibrarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LibrarianRepository librarianRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        Librarian librarian = librarianRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), librarian.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }
}
