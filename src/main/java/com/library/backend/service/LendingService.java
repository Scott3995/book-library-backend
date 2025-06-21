package com.library.backend.service;

import com.library.backend.model.BookCopy;
import com.library.backend.model.LendingRecord;
import com.library.backend.model.Member;
import com.library.backend.repository.BookCopyRepository;
import com.library.backend.repository.LendingRepository;
import com.library.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LendingService {

    private final LendingRepository lendingRepo;
    private final MemberRepository memberRepo;
    private final BookCopyRepository bookCopyRepo;

    public LendingRecord lendBook(Long memberId, Long bookCopyId) {
        Member member = memberRepo.findById(memberId).orElseThrow();
        BookCopy copy = bookCopyRepo.findById(bookCopyId).orElseThrow();

        if (!copy.getStatus().equals(BookCopy.Status.AVAILABLE))
            throw new IllegalStateException("Book not available");

        copy.setStatus(BookCopy.Status.BORROWED);

        LendingRecord record = new LendingRecord();
        record.setMember(member);
        record.setBookCopy(copy);
        record.setDueDate(LocalDate.now().plusDays(14));

        bookCopyRepo.save(copy);
        return lendingRepo.save(record);
    }

    public LendingRecord returnBook(Long lendingId) {
        LendingRecord record = lendingRepo.findById(lendingId).orElseThrow();
        BookCopy copy = record.getBookCopy();

        if (record.isReturned())
            throw new IllegalStateException("Already returned");

        record.setReturned(true);
        LocalDate today = LocalDate.now();

        if (today.isAfter(record.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), today);
            record.setFineAmount((double) daysLate); // cast long to Double
        }

        copy.setStatus(BookCopy.Status.AVAILABLE);
        bookCopyRepo.save(copy);
        return lendingRepo.save(record);
    }
    public LendingRecord payFine(Long lendingId) {
        LendingRecord record = lendingRepo.findById(lendingId).orElseThrow();

        if (!record.isReturned()) {
            throw new IllegalStateException("Book must be returned before paying fine.");
        }

        if (record.getFineAmount() <= 0) {
            throw new IllegalStateException("No fine to pay.");
        }

        record.setFineAmount(0.0);
        return lendingRepo.save(record);
    }

}
