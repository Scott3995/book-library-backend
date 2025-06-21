package com.library.backend.repository;

import com.library.backend.model.LendingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<LendingRecord, Long> {
}