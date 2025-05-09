package wood.mike.sbstravaapi.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ActivityFormatter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public String formatDuration(int seconds) {
        return formatDuration((double) seconds);
    }

    public String formatDuration(double seconds) {
        int totalSeconds = (int) Math.floor(seconds);
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, secs);
        } else {
            return String.format("%02d:%02d", minutes, secs);
        }
    }

    public String formatDistance(Float meters) {
        return meters == null ? "-" : String.format("%.2f km", meters / 1000f);
    }

    public String formatDistance(Double meters) {
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
