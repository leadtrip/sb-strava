package wood.mike.sbstravaapi.controllers.athlete;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

import java.util.Optional;

@Controller
public class AthleteHomeController {

    private final AthleteService athleteService;
    private final HttpSession httpSession;

    public AthleteHomeController(AthleteService athleteService, HttpSession httpSession) {
        this.athleteService = athleteService;
        this.httpSession = httpSession;
    }

    @GetMapping("/athlete")
    public String athleteHome(Model model) {
        Long stravaAthleteId = (Long) httpSession.getAttribute(Constants.STRAVA_ATHLETE_ID);
        Optional<Athlete> athlete = athleteService.getAthlete(stravaAthleteId);
        if (athlete.isPresent()) {
            model.addAttribute("athlete", athlete.get());
            return "athlete/home";
        } else {
            model.addAttribute("errorMessage", "Athlete not found");
            return "redirect:/error";
        }
    }
}
