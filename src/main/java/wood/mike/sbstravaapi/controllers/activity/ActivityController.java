package wood.mike.sbstravaapi.controllers.activity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.services.activity.ActivityService;

@Controller
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activity/{id}")
    public String getActivity(@PathVariable("id") long id, Model model) {
        Activity activity = activityService.getActivity(id);
        model.addAttribute("pageTitle", "Activity Details");
        model.addAttribute("templateName", "activity/activity");
        model.addAttribute("activity", activity);
        return "layout";
    }
}
