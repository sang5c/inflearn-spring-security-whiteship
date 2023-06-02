package com.example.inflearnspringsecuritywhiteship.form;

import com.example.inflearnspringsecuritywhiteship.common.SecurityLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Controller
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "hello world");
        return "index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "info");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "hello " + principal.getName());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "hello admin, " + principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", "hello user, " + principal.getName());
        return "user";
    }

    /**
     * Callable을 반환하면 별도의 쓰레드에서 처리가 된다.
     * WebAsyncManagerIntegrationFilter가 동일 SecurityContext를 사용하도록 도와준다.
     */
    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");

        return () -> {
            SecurityLogger.log("Callable");
            return "async-handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");

        return "async-service";
    }
}
