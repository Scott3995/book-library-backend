package com.library.backend.controller;

import com.library.backend.model.LendingRecord;
import com.library.backend.service.LendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import com.library.backend.repository.LendingRepository;


@RestController
@RequestMapping("/lending")
@RequiredArgsConstructor
public class LendingController {

    private final LendingService lendingService;
    private final LendingRepository lendingRepo;

    @GetMapping
    public List<LendingRecord> getAllLendings() {
        return lendingRepo.findAll();
    }


    @PostMapping("/lend")
    public LendingRecord lend(@RequestBody Map<String, Object> payload) {
        Long memberId = Long.valueOf(payload.get("memberId").toString());
        Long bookCopyId = Long.valueOf(payload.get("bookCopyId").toString());
        return lendingService.lendBook(memberId, bookCopyId);
    }

    @PostMapping("/return/{id}")
    public LendingRecord returnBook(@PathVariable Long id) {
        return lendingService.returnBook(id);
    }
    @PostMapping("/pay-fine/{id}")
    public LendingRecord payFine(@PathVariable Long id) {
        return lendingService.payFine(id);
    }

}
