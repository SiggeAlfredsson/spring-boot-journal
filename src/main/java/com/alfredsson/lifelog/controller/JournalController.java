package com.alfredsson.lifelog.controller;

import com.alfredsson.lifelog.db.MysqlDatabase;
import com.alfredsson.lifelog.model.Journal;
import com.alfredsson.lifelog.repository.JournalRepository;
import com.alfredsson.lifelog.service.JournalService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("username")
@RequestMapping("/journal/")
public class JournalController {

    private JournalService journalService;

    @GetMapping("create")
    public String showAddNewPage() {
        return "submitjournal";
    }


    @PostMapping("add")
    public String addEntry(HttpSession session, @RequestParam String title, @RequestParam String content) {

        if(session.getAttribute("username") ==null) {

        }

        String username = (String) session.getAttribute("username");

        System.out.println(title);
        System.out.println(content);
        System.out.println(session.getAttribute("username"));
        System.out.println(LocalDate.now());

        JournalRepository.addContent(username, title, content);

        return "journal";
    }
}
