package wood.mike.sbstravaapi.services.activity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.mappers.activity.ActivityMapper;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.ActivitySource;
import wood.mike.sbstravaapi.repositories.activity.ActivitySpecification;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.activity.ActivityTransformer;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityTransformer activityTransformer;
    private final StravaService stravaService;
    private final AthleteService athleteService;
    private final ActivityMapper activityMapper;
    private final ActivityStreamDataService activityStreamDataService;

    public ActivityService(ActivityRepository activityRepository,
                           ActivityTransformer activityTransformer,
                           StravaService stravaService,
                           AthleteService athleteService,
                           ActivityMapper activityMapper,
                           ActivityStreamDataService activityStreamDataService) {
        this.activityRepository = activityRepository;
        this.activityTransformer = activityTransformer;
        this.stravaService = stravaService;
        this.athleteService = athleteService;
        this.activityMapper = activityMapper;
        this.activityStreamDataService = activityStreamDataService;
    }

    public Activity getActivity(Long stravaActivityId, ActivitySource source) {

        Activity activity = activityRepository
                .findByStravaActivityId(stravaActivityId)
                .orElseGet(() -> {
                    log.info("Strava activity {} not found, fetching from Strava as {}", stravaActivityId, source);

                    Activity newActivity =
                            activityTransformer.toEntity(
                                    stravaService.getActivityData(String.valueOf(stravaActivityId))
                            );

                    newActivity.setSource(source);
                    return activityRepository.save(newActivity);
                });

        if (source == ActivitySource.SYNC) {
            boolean hasStreams = activityStreamDataService.existsByActivityId(activity.getId());
            if (!hasStreams) {
                log.info("No stream data found for activity {}, fetching from Strava", stravaActivityId);
                activityStreamDataService.fetchAndStoreActivityStreams(
                        activity.getId(),
                        String.valueOf(stravaActivityId)
                );
            }
        }

        return activity;
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

    public Integer syncActivities(int totalPagesToSync) {
        Athlete athlete = athleteService.getCurrentlyLoggedInAthleteOrThrow();
        AtomicInteger totalSynced = new AtomicInteger();

        for (int i = 0; i < totalPagesToSync; i++) {
            LocalDateTime oldestStartDate = activityRepository
                    .findFirstByAthleteAndSourceOrderByStartDateAsc(athlete, ActivitySource.SYNC)
                    .map(Activity::getStartDate)
                    .orElse(LocalDateTime.now());

            log.info("Oldest activity start date is {}", oldestStartDate);

            List<ActivityDto> activities = stravaService.getActivitiesBefore(oldestStartDate.toEpochSecond(ZoneOffset.UTC));
            log.info("Fetched {} activities", activities.size());

            for (ActivityDto activityDto : activities) {
                Optional<Activity> existingActivityOptional = activityRepository.findByStravaActivityId(activityDto.getId());

                Activity activityToSave;
                if (existingActivityOptional.isPresent()) {
                    Activity existingActivity = existingActivityOptional.get();
                    activityMapper.updateActivityFromDto(activityDto, existingActivity);
                    activityToSave = existingActivity;
                    log.info("Updating existing activity: {}", activityToSave.getStravaActivityId());
                } else {
                    activityToSave = activityTransformer.toEntity(activityDto);
                    log.info("Creating new activity: {}", activityToSave.getStravaActivityId());
                    totalSynced.getAndIncrement();
                }
                activityRepository.save(activityToSave);
            }

            if (activities.isEmpty()) {
                break;
            }
        }

        return totalSynced.get();
    }

}
