package wood.mike.sbstravaapi.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.services.auth.StravaAuthService;

@Controller
public class LoginController {

    private final StravaAuthService stravaAuthService;

    public LoginController(StravaAuthService stravaAuthService) {
        this.stravaAuthService = stravaAuthService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("stravaAuthUrl", stravaAuthService.getStravaAuthUrl());
        return "login";
    }
}