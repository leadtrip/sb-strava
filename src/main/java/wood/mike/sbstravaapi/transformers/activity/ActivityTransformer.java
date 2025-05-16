package wood.mike.sbstravaapi.transformers.activity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.mappers.activity.ActivityMapper;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Slf4j
@Service
public class ActivityTransformer {

    private final AthleteService athleteService;
    private final ActivityMapper activityMapper;

    public ActivityTransformer(AthleteService athleteService,
                               ActivityMapper activityMapper) {
        this.athleteService = athleteService;
        this.activityMapper = activityMapper;
    }

    public Activity toEntity(ActivityDto activityDto) {
        Activity activity = activityMapper.toEntity(activityDto);
        activity.setAthlete(athleteService.getAthleteByStravaId(activityDto.getAthlete().getStravaAthleteId()).orElseThrow(() -> new RuntimeException("Athlete not found")));
        return activity;
    }
}
