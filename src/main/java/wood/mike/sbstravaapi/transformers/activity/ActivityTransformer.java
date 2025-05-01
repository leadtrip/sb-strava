package wood.mike.sbstravaapi.transformers.activity;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Service
public class ActivityTransformer {

    private final AthleteService athleteService;

    public ActivityTransformer(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    public Activity
    toEntity(ActivityDto activityDto) {
        Activity activityEntity = new Activity();
        activityEntity.setStravaActivityId(activityDto.getId());
        activityEntity.setAthlete(athleteService.getAthleteByStravaId(activityDto.getAthlete().getStravaAthleteId()).orElseThrow(() -> new RuntimeException("Athlete not found")));
        activityEntity.setName(activityDto.getName());
        activityEntity.setDistance(activityDto.getDistance());
        activityEntity.setMovingTime(activityDto.getMovingTime());
        activityEntity.setElapsedTime(activityDto.getElapsedTime());
        activityEntity.setTotalElevationGain(activityDto.getTotalElevationGain());
        activityEntity.setElevHigh(activityDto.getElevHigh());
        activityEntity.setElevLow(activityDto.getElevLow());
        activityEntity.setStartDate(activityDto.getStartDate());
        activityEntity.setSportType(activityDto.getSportType());
        activityEntity.setTimezone(activityDto.getTimezone());
        activityEntity.setAverageSpeed(activityDto.getAverageSpeed());
        activityEntity.setMaxSpeed(activityDto.getMaxSpeed());
        activityEntity.setGearId(activityDto.getGearId());
        activityEntity.setKilojoules(activityDto.getKilojoules());
        activityEntity.setAverageWatts(activityDto.getAverageWatts());
        activityEntity.setMaxWatts(activityDto.getMaxWatts());
        activityEntity.setWeightedAverageWatts(activityDto.getWeightedAverageWatts());
        activityEntity.setSufferScore(activityDto.getSufferScore());
        return activityEntity;
    }
}
