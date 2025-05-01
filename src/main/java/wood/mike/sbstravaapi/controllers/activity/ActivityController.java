package wood.mike.sbstravaapi.controllers.activity;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.services.activity.ActivityService;
import wood.mike.sbstravaapi.services.strava.StravaService;

@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final StravaService stravaService;
    private final HttpSession httpSession;

    public ActivityController(ActivityService activityService, StravaService stravaService, HttpSession httpSession) {
        this.activityService = activityService;
        this.stravaService = stravaService;
        this.httpSession = httpSession;
    }

    @GetMapping("/activity")
    public String activity(Model model) {
        return "activity";
    }
}
