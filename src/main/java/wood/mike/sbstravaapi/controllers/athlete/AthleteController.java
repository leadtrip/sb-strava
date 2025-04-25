package wood.mike.sbstravaapi.controllers.athlete;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.activity.ActivityService;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller("/athlete")
public class AthleteController {

    private final AthleteService athleteService;
    private final ActivityService activityService;
    private final StravaService stravaService;
    private final HttpSession httpSession;

    public AthleteController(AthleteService athleteService, ActivityService activityService, StravaService stravaService, HttpSession httpSession) {
        this.athleteService = athleteService;
        this.activityService = activityService;
        this.stravaService = stravaService;
        this.httpSession = httpSession;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Long athleteId = (Long) httpSession.getAttribute(Constants.ATHLETE_ID);
        log.info("Retrieved athlete ID from session: {}", athleteId);
        Optional<Athlete> athlete = athleteService.getAthlete(athleteId);
        if (athlete.isPresent()) {
            List<?> activities = activityService.getLatestActivities(athlete.get(), 10);
            if(activities.isEmpty()) {
                log.info("No activities found locally, fetching from Strava");
                activities = stravaService.getActivities(1, 10, athleteId);
            }
            model.addAttribute("athlete", athlete.get());
            model.addAttribute("activities", activities);
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
