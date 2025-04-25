package wood.mike.sbstravaapi.controllers.athlete;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

import java.util.Optional;

@Slf4j
@Controller("/athlete")
public class AthleteController {

    private final AthleteService athleteService;
    private final HttpSession httpSession;

    public AthleteController(AthleteService athleteService, HttpSession httpSession) {
        this.athleteService = athleteService;
        this.httpSession = httpSession;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Long stravaAthleteId = (Long) httpSession.getAttribute(Constants.STRAVA_ATHLETE_ID);
        log.info("Retrieved stravaAthleteId from session: {}", stravaAthleteId);
        Optional<Athlete> athlete = athleteService.getAthlete(stravaAthleteId);
        if (athlete.isPresent()) {
            log.info("Getting profile for athlete: {}", athlete.get());
            model.addAttribute("athlete", athlete.get());
            model.addAttribute("pageTitle", "Athlete Profile");
            model.addAttribute("templateName", "athlete/profile");
        } else {
            log.error("Could not find athlete with ID {}", stravaAthleteId);
            model.addAttribute("pageTitle", "Error");
            model.addAttribute("templateName", "error");
            model.addAttribute("errorMessage", "Athlete not found");
        }
        return "layout";
    }
}
