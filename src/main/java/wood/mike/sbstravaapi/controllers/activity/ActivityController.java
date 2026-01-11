package wood.mike.sbstravaapi.controllers.activity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.activity.ActivityRow;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.filters.activity.ActivityFilter;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.ActivitySource;
import wood.mike.sbstravaapi.services.activity.ActivityService;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.activity.ActivityTransformer;
import wood.mike.sbstravaapi.utils.ActivityFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final AthleteService athleteService;
    private final StravaService stravaService;
    private final ActivityRepository activityRepository;
    private final ActivityTransformer activityTransformer;
    private final ActivityFormatter activityFormatter;

    public ActivityController(ActivityService activityService,
                              AthleteService athleteService,
                              StravaService stravaService,
                              ActivityRepository activityRepository,
                              ActivityTransformer activityTransformer,
                              ActivityFormatter activityFormatter) {
        this.activityService = activityService;
        this.athleteService = athleteService;
        this.stravaService = stravaService;
        this.activityRepository = activityRepository;
        this.activityTransformer = activityTransformer;
        this.activityFormatter = activityFormatter;
    }

    @GetMapping("/activity/strava")
    public String getStravaActivities(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      Model model) {
        Optional<Athlete> athleteOpt = athleteService.getCurrentlyLoggedInAthlete();

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

        // If the requested page has fewer than pageSize results, fetch more from Strava
        if (activityPage.isEmpty() || activityPage.getNumberOfElements() < size) {
            log.info("Fetching more activities from Strava...");

            List<ActivityDto> fetched = stravaService.getActivities(page + 1, size);
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
        model.addAttribute("pageTitle", "Activities");
        model.addAttribute("templateName", "activity/index");
        model.addAttribute("leftSidebarFragment", "charts/sidebarActivityTypeChart");

        return "layout";
    }


    @GetMapping("/activity/{id}")
    public String getActivity(@PathVariable("id") long id, Model model) {
        ActivityRow activity = new ActivityRow(activityService.getActivity(id, ActivitySource.SYNC), activityFormatter);
        model.addAttribute("pageTitle", "Activity Details");
        model.addAttribute("templateName", "activity/activity");
        model.addAttribute("activity", activity);
        return "layout";
    }

    @GetMapping("/activity/local")
    public String getLocalActivities(Model model) {
        model.addAttribute("pageTitle", "Activities");
        model.addAttribute("templateName", "activity/localActivities");
        model.addAttribute("leftSidebarFragment", "charts/sidebarActivityTypeChart");
        return "layout";
    }

    @GetMapping("/filteredactivities")
    public ResponseEntity<Map<String, Object>> getFilteredActivities(ActivityFilter filter) {

        int page = filter.getStart() / filter.getLength();

        Page<Activity> activities =
                activityService.findFiltered(page, filter.getLength(), filter);

        List<ActivityRow> rows = activities.getContent().stream()
                .map(activity -> new ActivityRow(activity, activityFormatter))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("draw", filter.getDraw());
        response.put("recordsTotal", activityService.countAll());
        response.put("recordsFiltered", activities.getTotalElements());
        response.put("data", rows);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/activity/sync")
    public String sync(Model model) {
        model.addAttribute("pageTitle", "Sync activities");
        model.addAttribute("templateName", "activity/sync");
        return "layout";
    }

    @PostMapping("/activity/syncactivities")
    public ResponseEntity<Integer> syncActivities(@RequestParam int totalPagesToSync) {
        log.info("Syncing activities...");
        Integer totalSynced = activityService.syncActivities(totalPagesToSync);
        return ResponseEntity.ok(totalSynced);
    }
}
