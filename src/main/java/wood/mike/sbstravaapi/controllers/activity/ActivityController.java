package wood.mike.sbstravaapi.controllers.activity;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.services.activity.ActivityService;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.activity.ActivityTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final AthleteService athleteService;
    private final StravaService stravaService;
    private final ActivityRepository activityRepository;
    private final ActivityTransformer activityTransformer;

    public ActivityController(ActivityService activityService, AthleteService athleteService, StravaService stravaService, ActivityRepository activityRepository, ActivityTransformer activityTransformer) {
        this.activityService = activityService;
        this.athleteService = athleteService;
        this.stravaService = stravaService;
        this.activityRepository = activityRepository;
        this.activityTransformer = activityTransformer;
    }

    @GetMapping("/activities")
    public String activities(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model,
                             HttpSession session) {

        Long athleteId = (Long) session.getAttribute(Constants.ATHLETE_ID);
        Optional<Athlete> athleteOpt = athleteService.getAthlete(athleteId);

        if (athleteOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Athlete not found");
            model.addAttribute("pageTitle", "Error");
            model.addAttribute("templateName", "error");
            return "layout";
        }

        Athlete athlete = athleteOpt.get();

        // Fetch from DB
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());
        Page<Activity> activityPage = activityRepository.findByAthlete(athlete, pageable);

        // If requested page has fewer than pageSize results, fetch more from Strava
        if (activityPage.isEmpty() || activityPage.getNumberOfElements() < size) {
            log.info("Fetching more activities from Strava...");

            List<ActivityDto> fetched = stravaService.getActivities(page, size);
            for (ActivityDto activityDto : fetched) {
                if (!activityRepository.existsByStravaActivityId(activityDto.getId())) {
                    activityRepository.save(activityTransformer.toEntity(activityDto));
                }
            }

            activityPage = activityRepository.findByAthlete(athlete, pageable); // Re-fetch
        }

        model.addAttribute("activities", activityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activityPage.getTotalPages());
        model.addAttribute("pageTitle", "All Activities");
        model.addAttribute("templateName", "activity/index");

        return "layout";
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
