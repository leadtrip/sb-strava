package wood.mike.sbstravaapi.repositories.activity;

import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

@Getter
public class WeeklyStatistic {

    private final Integer year;
    private final Integer week;
    private final Number total;
    private final LocalDate weekStartDate;

    public WeeklyStatistic(Integer year, Integer week, Number total) {
        this.year = year;
        this.week = week;
        this.total = total;
        this.weekStartDate = getStartOfWeek(year, week);
    }

    private LocalDate getStartOfWeek(Integer year, Integer week) {
        return LocalDate
                .ofYearDay(year, 1)
                .with(WeekFields.ISO.weekOfYear(), week)
                .with(WeekFields.ISO.dayOfWeek(), 1);
    }

    public Long getTotalAsLong() {
        return total.longValue();
    }

}


