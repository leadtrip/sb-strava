package wood.mike.sbstravaapi.repositories.activity;

import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

@Getter
public class WeeklySufferScore {

    private final int year;
    private final int week;
    private final long totalScore;
    private final LocalDate weekStartDate;

    public WeeklySufferScore(int year, int week, long totalScore) {
        this.year = year;
        this.week = week;
        this.totalScore = totalScore;
        this.weekStartDate = getStartOfWeek(year, week);
    }

    private LocalDate getStartOfWeek(int year, int week) {
        return LocalDate
                .ofYearDay(year, 1)
                .with(WeekFields.ISO.weekOfYear(), week)
                .with(WeekFields.ISO.dayOfWeek(), 1);
    }

}


