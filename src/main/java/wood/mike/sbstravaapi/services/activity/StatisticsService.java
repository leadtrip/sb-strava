package wood.mike.sbstravaapi.services.activity;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.WeeklyStatistic;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final ActivityRepository activityRepository;

    public StatisticsService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Map<String, List<?>> getWeeklyStatistics(String reportType, Athlete athlete) {
        List<WeeklyStatistic> weeklyStatistics =  fetchWeeklyStats(reportType, athlete).reversed();
        List<String> labels = weeklyStatistics.stream()
                .map(stat -> stat.getWeekStartDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .toList();

        List<Number> values = weeklyStatistics.stream()
                .map(WeeklyStatistic::getTotal)
                .toList();

        return Map.of(
                "labels", labels,
                "values", values
        );
    }

    List<WeeklyStatistic> fetchWeeklyStats(String reportType, Athlete athlete) {
        return switch (reportType) {
            case "load" -> this.activityRepository.sumSufferScoreByWeek(athlete);
            case "distance" -> this.activityRepository.sumDistanceByWeek(athlete);
            default -> throw new RuntimeException("Unknown report type");
        };
    }
}
