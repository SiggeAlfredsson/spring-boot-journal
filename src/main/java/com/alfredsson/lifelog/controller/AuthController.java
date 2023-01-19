package com.alfredsson.lifelog.controller;

import com.alfredsson.lifelog.repository.JournalRepository;
import com.alfredsson.lifelog.repository.UserRepository;
import com.alfredsson.lifelog.service.JournalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@Controller
@SessionAttributes("username")
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
        return "Something went wrong, <a href='/register'>try again</a>";
    }

    @GetMapping("error/register/0")
    @ResponseBody
    public String errorRegisterHandlerSpace() {
        return "No spaces allowed in username or password, <a href='/register'>try again</a>";
    }

    @GetMapping("error/register/1")
    @ResponseBody
    public String errorRegisterHandlerToShort() {
        return "Username or password to short, username needs min3 and password needs min8, <a href='/register'>try again</a>";
    }

    @GetMapping("error/register/2")
    @ResponseBody
    public String errorRegisterHandlerAlreadyExist() {
        return "Username already in use, <a href='/register'>try again</a>";
    }

    @PostMapping("register")
    public String register(HttpSession session, @RequestParam String username, @RequestParam String password) {
        if(username.contains(" ") || password.contains(" ") ) {
            return "redirect:error/register/0";
        }

        if(username.length()<3 || password.length() < 8 ) {
            return "redirect:error/register/1";
        }

        if(UserRepository.usernameExists(username)) {
            return "redirect:error/register/2";
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

        if (session.getAttribute("username") != null) {
            System.out.println("already logged in");
            return "journal";

        } else

        if(UserRepository.login(username, password)){
            session.setMaxInactiveInterval(60*30);
            session.setAttribute("username", username);

            return "journal";
        }  else {

                Object loginAttempts = session.getAttribute("login-attempts");
                if (loginAttempts == null) {
                    loginAttempts = 0;
                }

                session.setAttribute("login-attempts", (int) loginAttempts + 1);

                return "redirect:error/login?attempts=" + ((int) loginAttempts + 1);
            }
        }

    @PostMapping("logout")
    public String logout(HttpSession session) throws IOException {
        session.invalidate(); //Invalidate - empty the session
        return "redirect:/index.html";
    }
}
