package wood.mike.sbstravaapi.services.activity;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.WeeklySufferScore;

import java.util.List;

@Service
public class StatisticsService {

    private final ActivityRepository activityRepository;

    public StatisticsService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<WeeklySufferScore> getWeeklySufferScores(Athlete athlete) {
        return this.activityRepository.sumSufferScoreByWeek(athlete);
    }
}
