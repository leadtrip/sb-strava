package wood.mike.sbstravaapi.controllers.athlete;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.dtos.activity.ActivityStatsDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller("/athlete")
public class AthleteController {

    private final AthleteService athleteService;
    private final StravaService stravaService;
    private final HttpSession httpSession;

    public AthleteController(AthleteService athleteService,
                             StravaService stravaService,
                             HttpSession httpSession) {
        this.athleteService = athleteService;
        this.stravaService = stravaService;
        this.httpSession = httpSession;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Long athleteId = (Long) httpSession.getAttribute(Constants.ATHLETE_ID);
        Optional<Athlete> athlete = athleteService.getAthlete(athleteId);
        if (athlete.isPresent()) {
            ActivityStatsDto activityStats = stravaService.getAthleteStats(athlete.get().getStravaAthleteId());
            model.addAttribute("athlete", athlete.get());
            model.addAttribute("activityStats", activityStats);
            model.addAttribute("pageTitle", "Athlete Profile");
            model.addAttribute("templateName", "athlete/profile");
        } else {
            log.error("Could not find athlete with ID {}", athleteId);
            model.addAttribute("pageTitle", "Error");
            model.addAttribute("templateName", "error");
            model.addAttribute("errorMessage", "Athlete not found");
        }
        return "layout";
    }
}
