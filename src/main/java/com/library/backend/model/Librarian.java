package com.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
}
