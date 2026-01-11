package wood.mike.sbstravaapi.filters.activity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ActivityFilter {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime to;

    private String activityType;

    private Double targetDistance;
    private Double targetDistanceTolerance;

    // DataTables
    private Integer draw;
    private Integer start;
    private Integer length;
    private String sortField;
    private String sortDir;
}


