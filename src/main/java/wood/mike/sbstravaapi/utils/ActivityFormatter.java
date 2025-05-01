package wood.mike.sbstravaapi.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ActivityFormatter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public String formatDuration(int seconds) {
        return String.format("%02d:%02d:%02d",
                seconds / 3600,
                (seconds % 3600) / 60,
                seconds % 60);
    }

    public String formatDistance(Float meters) {
        return meters == null ? "-" : String.format("%.2f km", meters / 1000f);
    }

    public String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "-";
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public String roundWithUnit(Number number, String unit) {
        if (number == null) return "-";
        long rounded = Math.round(number.doubleValue());
        return rounded + " " + unit;
    }

    public String formatSpeed(Float metersPerSecond) {
        if (metersPerSecond == null) return "-";
        float kmPerHour = metersPerSecond * 3.6f;
        return String.format("%.1f km/h", kmPerHour);
    }

}
