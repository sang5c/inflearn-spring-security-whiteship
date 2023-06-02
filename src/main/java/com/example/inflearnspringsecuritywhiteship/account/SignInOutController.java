package com.example.inflearnspringsecuritywhiteship.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInOutController {

    @GetMapping("/signin")
    public String signinForm() {
        return "login";
    }
}
