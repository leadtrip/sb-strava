package wood.mike.sbstravaapi.unit.services.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.WeeklyStatistic;
import wood.mike.sbstravaapi.services.activity.StatisticsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    public void testGetWeeklyStatistics_fills_in_missing_weeks() {
        String reportType = "distance";
        Athlete athlete = new Athlete();
        LocalDate from = LocalDate.of(2026, 5, 1);
        LocalDate to = LocalDate.of(2026, 5, 30);
        String activityType = null;

        // purposely leave out weeks 19 and 21
        List<WeeklyStatistic> weeklyStats = List.of(
                new WeeklyStatistic(2026, 18, 190),
                new WeeklyStatistic(2026, 20, 81),
                new WeeklyStatistic(2026, 21, 301));

        when(activityRepository.sumDistanceByWeek(same(athlete), any(LocalDateTime.class), any(LocalDateTime.class), isNull())).thenReturn(weeklyStats);

        Map<String, List<?>> weeklyStatistics = statisticsService.getWeeklyStatistics(reportType, athlete, from, to, activityType);

        System.out.println(weeklyStatistics);

        assertThat(weeklyStatistics.get("labels")).isEqualTo(List.of("01 May 2026", "08 May 2026", "15 May 2026", "22 May 2026", "29 May 2026"));
        assertThat(weeklyStatistics.get("values")).isEqualTo(List.of(190,0,81,301,0));
    }
}
