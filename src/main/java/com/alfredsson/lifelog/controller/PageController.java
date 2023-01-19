package com.alfredsson.lifelog.controller;

import com.alfredsson.lifelog.service.JournalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/journal/page/*")
public class PageController {

    private JournalService journalService;

    @GetMapping("{date}")
    public String showDateContent(HttpSession session, @PathVariable String date) {

        return "redirect:/journal/page/"+date;
    }
}
