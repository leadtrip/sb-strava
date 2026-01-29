package wood.mike.sbstravaapi.services.activity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wood.mike.sbstravaapi.dtos.activity.ActivityStatsDto;
import wood.mike.sbstravaapi.entities.activity.ActivityStats;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityStatsRepository;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.activity.ActivityStatsTransformer;

@Service
public class ActivityStatsService {

    private final StravaService stravaService;
    private final AthleteService athleteService;
    private final ActivityStatsRepository activityStatsRepository;
    private final ActivityStatsTransformer activityStatsTransformer;

    public ActivityStatsService(StravaService stravaService,
                                AthleteService athleteService,
                                ActivityStatsRepository activityStatsRepository,
                                ActivityStatsTransformer activityStatsTransformer) {
        this.stravaService = stravaService;
        this.athleteService = athleteService;
        this.activityStatsRepository = activityStatsRepository;
        this.activityStatsTransformer = activityStatsTransformer;
    }

    public ActivityStats fetchIfMissing() {
        Athlete athlete = athleteService.getCurrentlyLoggedInAthleteOrThrow();
        if(activityStatsRepository.findByAthlete(athlete).isEmpty()) {
            ActivityStatsDto activityStats = stravaService.getAthleteStats(athlete.getStravaAthleteId());
            activityStatsRepository.save(activityStatsTransformer.toEntity(activityStats));
        }
        return activityStatsRepository.findByAthlete(athlete).get();
    }

    @Transactional
    public ActivityStats fetchAndSync() {
        Athlete athlete = athleteService.getCurrentlyLoggedInAthleteOrThrow();
        ActivityStatsDto remoteDto = stravaService.getAthleteStats(athlete.getStravaAthleteId());

        ActivityStats entity = activityStatsRepository.findByAthlete(athlete)
                .map(existingEntity -> {
                    activityStatsTransformer.updateEntityFromDto(remoteDto, existingEntity);
                    return existingEntity;
                })
                .orElseGet(() -> activityStatsTransformer.toEntity(remoteDto));

        return activityStatsRepository.save(entity);
    }
}
