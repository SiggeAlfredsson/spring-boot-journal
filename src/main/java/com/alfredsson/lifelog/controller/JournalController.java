package com.alfredsson.lifelog.controller;

import com.alfredsson.lifelog.service.JournalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/journal/")
public class JournalController {

    private JournalService journalService;

    @GetMapping
    public String showContentPage() {
        return "journalForm.jsp";
    }

    @PostMapping
    public String addEntry(HttpSession session, @RequestParam String title, @RequestParam String Content) {

        return "redirect:/";
    }
}
