package wood.mike.sbstravaapi.utils;

import org.springframework.stereotype.Component;
import wood.mike.sbstravaapi.controllers.activity.PaceZone;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        if (meters == null) {
            return "-";
        }

        BigDecimal kms = new BigDecimal(meters / 1000f);
        BigDecimal truncatedKms = kms.setScale(2, RoundingMode.DOWN);

        return String.format("%.2f km", truncatedKms);
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

    public String round(Number number) {
        if (number == null) return "-";
        return String.valueOf(Math.round(number.doubleValue()));
    }

    public String formatSpeed(Float metersPerSecond) {
        if (metersPerSecond == null) return "-";
        float kmPerHour = metersPerSecond * 3.6f;
        return String.format("%.1f km/h", kmPerHour);
    }

    public String getPaceZone(Integer val) {
        return PaceZone.zoneForVal(val).getDisplay();
    }
}
