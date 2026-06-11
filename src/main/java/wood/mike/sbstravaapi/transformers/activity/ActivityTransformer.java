package wood.mike.sbstravaapi.transformers.activity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.mappers.activity.ActivityMapper;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.segments.SummarySegmentService;

@Slf4j
@Service
public class ActivityTransformer {

    private final AthleteService athleteService;
    private final ActivityMapper activityMapper;
    private final SummarySegmentService summarySegmentService;

    public ActivityTransformer(AthleteService athleteService,
                               ActivityMapper activityMapper,
                               SummarySegmentService summarySegmentService) {
        this.athleteService = athleteService;
        this.activityMapper = activityMapper;
        this.summarySegmentService = summarySegmentService;
    }

    public Activity toEntity(ActivityDto activityDto) {
        Activity activity = activityMapper.toEntity(activityDto, athleteService, summarySegmentService);
        activity.setAthlete(athleteService.getAthleteByStravaId(activityDto.getAthlete().getStravaAthleteId()).orElseThrow(() -> new RuntimeException("Athlete not found")));
        return activity;
    }

    public void updateActivityFromDto(ActivityDto dto, Activity activity) {
        activityMapper.updateActivityFromDto(dto, activity, athleteService, summarySegmentService);
    }
}
