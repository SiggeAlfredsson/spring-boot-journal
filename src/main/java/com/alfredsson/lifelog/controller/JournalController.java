package com.alfredsson.lifelog.controller;

import com.alfredsson.lifelog.service.JournalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/journal/")
public class JournalController {

    private JournalService journalService;

    @GetMapping
    public String showContentPage() {
        return "journalForm.jsp";
    }

    @PostMapping("update")
    public String addEntry(HttpSession session, @RequestParam String title, @RequestParam String content) {

        System.out.println(title);
        System.out.println(content);
        System.out.println(session.getAttribute("username"));
        System.out.println(LocalDate.now());

        return "redirect:/test";
    }
}
