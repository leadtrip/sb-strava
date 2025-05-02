package wood.mike.sbstravaapi.services.strava;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.athlete.AthleteRepository;
import wood.mike.sbstravaapi.transformers.activity.ActivityTransformer;

import java.util.List;

@Slf4j
@Service
public class StravaSyncService {

    private final ActivityTransformer activityTransformer;
    @Value("${strava.sync.activityFetchLimit}")
    private Integer activityFetchLimit;
    @Value("${strava.sync.maxPages:10}")
    private int maxPages;

    private final AthleteRepository athleteRepository;
    private final ActivityRepository activityRepository;
    private final StravaService stravaService;
    private final HttpSession httpSession;

    public StravaSyncService(AthleteRepository athleteRepository,
                             ActivityRepository activityRepository,
                             StravaService stravaService,
                             ActivityTransformer activityTransformer,
                             HttpSession httpSession) {
        this.athleteRepository = athleteRepository;
        this.activityRepository = activityRepository;
        this.stravaService = stravaService;
        this.activityTransformer = activityTransformer;
        this.httpSession = httpSession;
    }

    @Async
    @Scheduled(fixedRateString = "PT1H")
    public void scheduledSync() {
        log.info("Performing scheduled Strava activity sync");
        Long athleteId = (Long) httpSession.getAttribute(Constants.ATHLETE_ID);
        if (athleteId != null) {
            syncAthlete(athleteId, maxPages);
        } else {
            log.error("Not syncing, could not find athlete ID in session");
        }
    }

    public void syncAthlete(Long athleteId, int maxPages) {
        athleteRepository.findById(athleteId).ifPresent(athlete -> {
            for (int page = 1; page <= maxPages; page++) {
                List<ActivityDto> activities = stravaService.getActivities(page, activityFetchLimit);

                if (activities.isEmpty()) {
                    log.info("No more activities found at page {}", page);
                    break;
                }

                int newCount = 0;
                for (ActivityDto dto : activities) {
                    if (!activityRepository.existsByStravaActivityId(dto.getId())) {
                        activityRepository.save(activityTransformer.toEntity(dto));
                        newCount++;
                    }
                }

                log.info("Fetched page {}, new activities saved: {}", page, newCount);
            }
        });
    }

}
