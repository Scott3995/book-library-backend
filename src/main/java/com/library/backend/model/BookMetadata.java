package com.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookMetadata {

    @Id
    private String isbn; // Unique book identifier

    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private String genre;
}
