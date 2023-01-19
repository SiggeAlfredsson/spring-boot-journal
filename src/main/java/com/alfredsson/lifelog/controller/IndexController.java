package com.alfredsson.lifelog.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("login")
    public String showLoginPage(HttpSession session) {
        if(session.getAttribute("username") != null){
            return "journal";
        }
        return "login";
    }

    @GetMapping("register")
    public String showRegisterPage(HttpSession session) {
        session.invalidate();
        return "register";
    }


}
