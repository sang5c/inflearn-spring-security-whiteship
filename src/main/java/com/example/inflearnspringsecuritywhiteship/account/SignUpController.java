package com.example.inflearnspringsecuritywhiteship.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class SignUpController {

    private final AccountService accountService;

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("account", new Account());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute Account account) {
        account.setRole("USER");
        accountService.createNew(account);
        return "redirect:/";
    }
}
