package wood.mike.sbstravaapi.transformers.activity;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.activity.ActivityStatsDto;
import wood.mike.sbstravaapi.entities.activity.ActivityStats;
import wood.mike.sbstravaapi.mappers.activity.ActivityStatsMapper;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Service
public class ActivityStatsTransformer {

    private final AthleteService athleteService;
    private final ActivityStatsMapper activityStatsMapper;

    public ActivityStatsTransformer(AthleteService athleteService, ActivityStatsMapper activityStatsMapper) {
        this.athleteService = athleteService;
        this.activityStatsMapper = activityStatsMapper;
    }

    public ActivityStats toEntity(ActivityStatsDto dto) {
        ActivityStats entity = activityStatsMapper.toEntity(dto);
        entity.setAthlete(athleteService.getCurrentlyLoggedInAthleteOrThrow());
        return entity;
    }
}
