package com.example.guestbook.service;

import com.example.guestbook.entity.GuestbookEntry;
import com.example.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public GuestbookService(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    public GuestbookEntry saveEntry(GuestbookEntry entry) {
        return guestbookRepository.save(entry);
    }

    public Page<GuestbookEntry> getEntriesByPage(Pageable pageable) {
        return guestbookRepository.findAll(pageable);
    }

    public boolean deleteEntryIfPasswordMatches(Long id, String password) {
        Optional<GuestbookEntry> optionalEntry = guestbookRepository.findById(id);
        if (optionalEntry.isPresent()) {
            GuestbookEntry entry = optionalEntry.get();
            if (entry.getPassword().equals(password)) {
                guestbookRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public void likeEntry(Long id) {
        guestbookRepository.findById(id).ifPresent(entry -> {
            entry.increaseLikes();
            guestbookRepository.save(entry);
        });
    }

    public void addEmojiToEntry(Long id, String emoji) {
        guestbookRepository.findById(id).ifPresent(entry -> {
            entry.setEmoji(emoji);
            guestbookRepository.save(entry);
        });
    }
}
