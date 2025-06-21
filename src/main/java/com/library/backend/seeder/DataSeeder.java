package com.library.backend.seeder;

import com.library.backend.model.Librarian;
import com.library.backend.repository.LibrarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final LibrarianRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (repository.findByEmail("librarian@library.com").isEmpty()) {
            Librarian librarian = new Librarian();
            librarian.setEmail("librarian@library.com");
            librarian.setPassword(encoder.encode("SecureLib@123"));
            repository.save(librarian);
            System.out.println("✅ Default librarian created.");
        } else {
            System.out.println("ℹ️ Default librarian already exists.");
        }
    }
}
