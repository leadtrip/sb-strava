package wood.mike.sbstravaapi.utils;

import org.springframework.stereotype.Component;

@Component
public class TimeUtils {
    public String hms(int seconds) {
        return String.format("%02d:%02d:%02d",
                seconds / 3600,
                (seconds % 3600) / 60,
                seconds % 60);
    }
}
