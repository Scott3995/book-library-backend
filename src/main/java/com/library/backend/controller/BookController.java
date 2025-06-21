package com.library.backend.controller;

import com.library.backend.model.BookCopy;
import com.library.backend.model.BookMetadata;
import com.library.backend.repository.BookCopyRepository;
import com.library.backend.repository.BookMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookMetadataRepository metadataRepo;
    private final BookCopyRepository copyRepo;

    // ---------- METADATA ----------

    @PostMapping
    public ResponseEntity<?> createMetadata(@RequestBody BookMetadata book) {
        if (metadataRepo.existsById(book.getIsbn())) {
            return ResponseEntity.badRequest().body("Book with this ISBN already exists.");
        }
        return ResponseEntity.ok(metadataRepo.save(book));
    }

    @GetMapping
    public ResponseEntity<List<BookMetadata>> getAllMetadata() {
        return ResponseEntity.ok(metadataRepo.findAll());
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteMetadata(@PathVariable String isbn) {
        if (!metadataRepo.existsById(isbn)) {
            return ResponseEntity.notFound().build();
        }

        boolean hasCopies = copyRepo.findAll().stream()
                .anyMatch(c -> c.getMetadata().getIsbn().equals(isbn));

        if (hasCopies) {
            return ResponseEntity.badRequest().body("Cannot delete metadata. Copies still exist.");
        }

        metadataRepo.deleteById(isbn);
        return ResponseEntity.ok("Book metadata deleted.");
    }

    // ---------- COPIES ----------

    @PostMapping("/{isbn}/copies")
    public ResponseEntity<?> addCopy(@PathVariable String isbn, @RequestBody BookCopy copy) {
        if (!metadataRepo.existsById(isbn)) {
            return ResponseEntity.badRequest().body("Book metadata not found.");
        }
        BookMetadata book = metadataRepo.findById(isbn).get();
        copy.setMetadata(book);
        BookCopy saved = copyRepo.save(copy);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/{isbn}/copies")
    public ResponseEntity<List<BookCopy>> getCopies(@PathVariable String isbn) {
        List<BookCopy> copies = copyRepo.findAll().stream()
                .filter(c -> c.getMetadata().getIsbn().equals(isbn))
                .toList();
        return ResponseEntity.ok(copies);
    }

}
