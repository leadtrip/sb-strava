package wood.mike.sbstravaapi.services.strava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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

    private final AthleteRepository athleteRepository;
    private final ActivityRepository activityRepository;
    private final StravaService stravaService;

    public StravaSyncService(AthleteRepository athleteRepository,
                             ActivityRepository activityRepository,
                             StravaService stravaService, ActivityTransformer activityTransformer) {
        this.athleteRepository = athleteRepository;
        this.activityRepository = activityRepository;
        this.stravaService = stravaService;
        this.activityTransformer = activityTransformer;
    }

    @Scheduled(fixedRateString = "PT1H")
    public void scheduledSync() {
        log.info("Performing scheduled Strava activity sync");
        //syncAthlete();
    }

    public void syncAthlete(Long athleteId) {
        athleteRepository.findById(athleteId).ifPresent(athlete -> {
            List<ActivityDto> activities = stravaService.getActivities(1, activityFetchLimit);
            for (ActivityDto activityDto : activities) {
                if (!activityRepository.existsByStravaActivityId(activityDto.getId())) {
                    activityRepository.save(activityTransformer.toEntity(activityDto));
                }
            }
        });
    }

}
