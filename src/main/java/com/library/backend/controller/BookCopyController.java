package com.library.backend.controller;

import com.library.backend.model.BookCopy;
import com.library.backend.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-copies")
@RequiredArgsConstructor
public class BookCopyController {

    private final BookCopyRepository copyRepo;

    @GetMapping("/available")
    public List<BookCopy> getAvailableCopies() {
        return copyRepo.findByStatus(BookCopy.Status.AVAILABLE);
    }
    @GetMapping("/debug")
    public List<BookCopy> debug() {
        return copyRepo.findAll();
    }


}
