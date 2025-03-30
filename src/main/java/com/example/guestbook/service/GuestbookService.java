package com.example.guestbook.service;

import com.example.guestbook.entity.GuestbookEntry;
import com.example.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public GuestbookService(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    public List<GuestbookEntry> getAllEntries() {
        return guestbookRepository.findAll();
    }

    public GuestbookEntry saveEntry(GuestbookEntry entry) {
        return guestbookRepository.save(entry);
    }

    public void deleteEntry(Long id) {
        guestbookRepository.deleteById(id);
    }

    public Page<GuestbookEntry> getEntriesByPage(Pageable pageable) {
        return guestbookRepository.findAll(pageable);
    }
}
