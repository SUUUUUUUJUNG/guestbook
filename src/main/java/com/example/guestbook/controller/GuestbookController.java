package com.example.guestbook.controller;

import com.example.guestbook.entity.GuestbookEntry;
import com.example.guestbook.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

    private final GuestbookService guestbookService;

    public GuestbookController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping
    public String showGuestbook(Model model) {
        List<GuestbookEntry> entries = guestbookService.getAllEntries();
        model.addAttribute("entries", entries);
        return "guestbook";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute GuestbookEntry entry) {
        guestbookService.saveEntry(entry);
        return "redirect:/guestbook";
    }

    @GetMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        guestbookService.deleteEntry(id);
        return "redirect:/guestbook";
    }
}
