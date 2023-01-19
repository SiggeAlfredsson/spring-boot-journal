package com.alfredsson.lifelog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("register")
    public String showRegisterPage() {
        return "register";
    }


}
