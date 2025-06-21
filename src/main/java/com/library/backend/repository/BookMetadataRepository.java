package com.library.backend.repository;

import com.library.backend.model.BookMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMetadataRepository extends JpaRepository<BookMetadata, String> {
}
