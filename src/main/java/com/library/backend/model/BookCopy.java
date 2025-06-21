package com.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "metadata_isbn")
    private BookMetadata metadata;

    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    public enum Status {
        AVAILABLE,
        BORROWED
    }
}
