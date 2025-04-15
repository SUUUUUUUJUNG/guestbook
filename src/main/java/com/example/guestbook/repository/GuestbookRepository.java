package com.example.guestbook.repository;

import com.example.guestbook.entity.GuestbookEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface GuestbookRepository extends JpaRepository<GuestbookEntry, Long> {
    Page<GuestbookEntry> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
