package wood.mike.sbstravaapi.services.activity;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.WeeklyStatistic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final ActivityRepository activityRepository;

    public StatisticsService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Map<String, List<?>> getWeeklyStatistics(String reportType, Athlete athlete, LocalDate from, LocalDate to, String activityType) {
        LocalDateTime start = (from != null) ? from.atStartOfDay() : null;
        LocalDateTime end = (to != null) ? to.atTime(23, 59, 59) : null;

        List<WeeklyStatistic> weeklyStatistics = fetchWeeklyStats(reportType, athlete, start, end, activityType).reversed();

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

    private List<WeeklyStatistic> fetchWeeklyStats(String reportType, Athlete athlete, LocalDateTime from, LocalDateTime to, String activityType) {
        return switch (reportType) {
            case "load" -> this.activityRepository.sumSufferScoreByWeek(athlete, from, to, activityType);
            case "distance" -> this.activityRepository.sumDistanceByWeek(athlete, from, to, activityType);
            default -> throw new RuntimeException("Unknown report type");
        };
    }
}
