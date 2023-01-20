package com.alfredsson.lifelog.controller;

import com.alfredsson.lifelog.model.Journal;
import com.alfredsson.lifelog.repository.JournalRepository;
import com.alfredsson.lifelog.service.JournalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/journal/page/*")
public class PageController {

    private JournalService journalService;

    @GetMapping("{date}")
    public String showDateContent(Model model, HttpSession session, @RequestParam(value = "date") String date) {
        System.out.println(date);
        String username = (String) session.getAttribute("username");
        List<Journal> journals = JournalRepository.getContent(username, date);
        model.addAttribute("journals", journals);
        return "readjournal";
    }
}
