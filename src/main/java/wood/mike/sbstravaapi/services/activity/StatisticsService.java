package wood.mike.sbstravaapi.services.activity;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.WeeklySufferScore;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final ActivityRepository activityRepository;

    public StatisticsService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Map<String, List<?>> getWeeklySufferScores(Athlete athlete) {
        List<WeeklySufferScore> weeklyScores =  this.activityRepository.sumSufferScoreByWeek(athlete).reversed();
        List<String> labels = weeklyScores.stream()
                .map(score -> score.getWeekStartDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .toList();

        List<Long> values = weeklyScores.stream()
                .map(WeeklySufferScore::getTotalScore)
                .toList();

        return Map.of(
                "labels", labels,
                "values", values
        );
    }
}
