package com.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LendingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private BookCopy bookCopy;

    private LocalDate checkoutDate = LocalDate.now();
    private LocalDate dueDate;

    private boolean returned = false;

    private Double fineAmount = 0.0;

    private boolean finePaid = false;
}