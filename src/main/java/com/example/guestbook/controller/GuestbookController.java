package com.example.guestbook.controller;

import com.example.guestbook.entity.GuestbookEntry;
import com.example.guestbook.service.GuestbookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

    private final GuestbookService guestbookService;

    public GuestbookController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping
    public String showGuestbook(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String startDate,
                                @RequestParam(required = false) String endDate) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<GuestbookEntry> entryPage;

        if (startDate != null && endDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            entryPage = guestbookService.getEntriesByDate(start, end, pageable);
        } else {
            entryPage = guestbookService.getEntriesByPage(pageable);
        }

        model.addAttribute("entries", entryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", entryPage.getTotalPages());
        model.addAttribute("entry", new GuestbookEntry());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "guestbook";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute GuestbookEntry entry) {
        guestbookService.saveEntry(entry);
        return "redirect:/guestbook";
    }

    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id, @RequestParam String password, Model model) {
        boolean deleted = guestbookService.deleteEntryIfPasswordMatches(id, password);
        if (!deleted) {
            model.addAttribute("deleteError", "비밀번호가 일치하지 않습니다.");
        }
        return "redirect:/guestbook";
    }

    @PostMapping("/like/{id}")
    public String likeEntry(@PathVariable Long id) {
        guestbookService.likeEntry(id);
        return "redirect:/guestbook";
    }

    @PostMapping("/emoji/{id}")
    public String addEmoji(@PathVariable Long id, @RequestParam String emoji) {
        guestbookService.addEmojiToEntry(id, emoji);
        return "redirect:/guestbook";
    }

    @PostMapping("/comment/{id}")
    public String addComment(@PathVariable Long id,
                             @RequestParam String commenter,
                             @RequestParam String content) {
        guestbookService.addCommentToEntry(id, commenter, content);
        return "redirect:/guestbook";
    }

}
