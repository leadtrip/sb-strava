package wood.mike.sbstravaapi.controllers.athlete;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.dtos.activity.ActivityStatsDto;
import wood.mike.sbstravaapi.entities.activity.ActivityStats;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.activity.ActivityStatsService;
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
    private final ActivityStatsService activityStatsService;

    public AthleteController(AthleteService athleteService,
                             StravaService stravaService,
                             HttpSession httpSession,
                             ActivityStatsService activityStatsService) {
        this.athleteService = athleteService;
        this.stravaService = stravaService;
        this.httpSession = httpSession;
        this.activityStatsService = activityStatsService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Long athleteId = (Long) httpSession.getAttribute(Constants.ATHLETE_ID);
        Optional<Athlete> athlete = athleteService.getAthlete(athleteId);
        if (athlete.isPresent()) {
            ActivityStats activityStats = activityStatsService.fetchIfMissing();
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
