package com.example.guestbook.repository;

import com.example.guestbook.entity.GuestbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<GuestbookEntry, Long> {
}
