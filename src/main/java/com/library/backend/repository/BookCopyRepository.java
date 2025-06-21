package com.library.backend.repository;

import com.library.backend.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByStatus(BookCopy.Status status);
}


