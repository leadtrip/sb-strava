package wood.mike.sbstravaapi.services.activity;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.WeeklyStatistic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final ActivityRepository activityRepository;

    public StatisticsService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Map<String, List<?>> getWeeklyStatistics(String reportType, Athlete athlete, LocalDate from, LocalDate to, String activityType) {
        if (from == null || to == null) {
            return getLegacyAllTimeStats(reportType, athlete, activityType);
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        List<WeeklyStatistic> dbStats = fetchWeeklyStats(reportType, athlete, start, end, activityType);

        Map<String, Number> dbMap = dbStats.stream().collect(Collectors.toMap(
                stat -> stat.getYear() + "-" + stat.getWeek(),
                WeeklyStatistic::getTotal,
                (existing, replacement) -> existing
        ));

        List<String> labels = new ArrayList<>();
        List<Number> values = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        LocalDate currentWeekStart = from;
        while (!currentWeekStart.isAfter(to)) {
            int year = currentWeekStart.get(IsoFields.WEEK_BASED_YEAR);
            int week = currentWeekStart.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            String key = year + "-" + week;

            labels.add(currentWeekStart.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
            values.add(dbMap.getOrDefault(key, 0));

            LocalDateTime weekFrom = currentWeekStart.atStartOfDay();
            LocalDateTime weekTo = currentWeekStart.plusDays(6).atTime(23, 59, 0);

            String fromIso = weekFrom.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).substring(0, 16);
            String toIso = weekTo.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).substring(0, 16);

            StringBuilder urlBuilder = new StringBuilder(
                    String.format("/activity/local?from=%s&to=%s", fromIso, toIso)
            );

            if (activityType != null && !activityType.trim().isEmpty()) {
                urlBuilder.append("&type=").append(activityType.trim());
            }

            urls.add(urlBuilder.toString());

            currentWeekStart = currentWeekStart.plusWeeks(1);
        }

        return Map.of(
                "labels", labels,
                "values", values,
                "urls", urls
        );
    }

    public Map<String, List<?>> getLegacyAllTimeStats(String reportType, Athlete athlete, String activityType) {

        List<WeeklyStatistic> weeklyStatistics = fetchWeeklyStats(reportType, athlete, null, null, activityType).reversed();

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
