package com.alfredsson.lifelog.controller;

import com.alfredsson.lifelog.repository.JournalRepository;
import com.alfredsson.lifelog.repository.UserRepository;
import com.alfredsson.lifelog.service.JournalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private JournalService journalService;


    @GetMapping("error/login")
    @ResponseBody
    public String errorLoginHandler(@RequestParam int attempts) {
        return "Invalid attempt {" + attempts + "}, <a href='/'>try again</a>";
    }
    @GetMapping("error/register")
    @ResponseBody
    public String errorRegisterHandler() {
        return "Username already in use or username/password are to short. Username needs atleast 3 characters and the password needs atleast 8, <a href='/'>try again</a>";
    }



    @PostMapping("register")
    public String register(HttpSession session, @RequestParam String username, @RequestParam String password) {
        if(username.length()<3 || password.length() < 8 ) {
            return "redirect:error/register";
        }

        if(UserRepository.usernameExists(username)) {
            return "redirect:error/register";
        } else {
            int result = UserRepository.createNew(username, password); // sql injection??
            if (result == 1) {
                return "redirect:/index.html";
            } else {
                return "redirect:error/register";
            }
        }
    }

    @PostMapping("login")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password) {

        System.out.println("potatis");
        if (session.getAttribute("username") != null) {
            System.out.println("already logged in");
            return "redirect:/journal.html";

        } else

        if(UserRepository.login(username, password)){
            session.setMaxInactiveInterval(60*30);
            session.setAttribute("username", username);

            System.out.println("Yayyy succes");
            return "redirect:/journal.html";
        }  else {

                Object loginAttempts = session.getAttribute("login-attempts");
                if (loginAttempts == null) {
                    loginAttempts = 0;
                }

                session.setAttribute("login-attempts", (int) loginAttempts + 1);
                System.out.println(username);
                return "redirect:error/login?attempts=" + ((int) loginAttempts + 1);
            }
        }



    @PostMapping("logout")
    public String logout(HttpSession session) throws IOException {
        session.invalidate(); //Invalidate - empty the session
        return "redirect:/index.html";
    }
}
