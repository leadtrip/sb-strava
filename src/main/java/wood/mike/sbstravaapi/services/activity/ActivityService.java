package wood.mike.sbstravaapi.services.activity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.ActivitySpecification;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.activity.ActivityTransformer;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityTransformer activityTransformer;
    private final StravaService stravaService;
    private final AthleteService athleteService;

    public ActivityService(ActivityRepository activityRepository,
                           ActivityTransformer activityTransformer,
                           StravaService stravaService,
                           AthleteService athleteService) {
        this.activityRepository = activityRepository;
        this.activityTransformer = activityTransformer;
        this.stravaService = stravaService;
        this.athleteService = athleteService;
    }

    public Activity getActivity(Long stravaActivityId) {
        return activityRepository.findByStravaActivityId(stravaActivityId).orElseGet(() -> {
            log.info("Strava activity id {} not found in database, fetching from Strava", stravaActivityId);
            Activity activity = activityTransformer.toEntity(stravaService.getActivityData(String.valueOf(stravaActivityId)));
            activityRepository.save(activity);
            return activity;
        });
    }

    public List<Activity> getLatestActivities(Athlete athlete, int numberOfActivities) {
        Pageable pageable = PageRequest.of(0, numberOfActivities, Sort.by("startDate").descending());
        return activityRepository.findByAthleteOrderByStartDateDesc(athlete, pageable);
    }

    public Page<Activity> findFiltered(int page, int size, LocalDateTime from, LocalDateTime to, String type) {
        log.info("Searching for filtered activities between {} and {} with type {}", from, to, type);
        Athlete athlete = athleteService.getCurrentlyLoggedInAthlete().orElseThrow(() -> new RuntimeException("Athlete not found"));
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());
        return activityRepository.findAll(ActivitySpecification.withFilters(from, to, type, athlete), pageable);
    }

    public long countFiltered(LocalDateTime from, LocalDateTime to, String type) {
        Athlete athlete = athleteService.getCurrentlyLoggedInAthlete().orElseThrow(() -> new RuntimeException("Athlete not found"));
        return activityRepository.count(ActivitySpecification.withFilters(from, to, type, athlete));
    }

    public long countAll() {
        return activityRepository.count();
    }
}
