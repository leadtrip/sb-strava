package wood.mike.sbstravaapi.dtos.segments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SummarySegmentEffortDto {
    @JsonProperty("id")
    private Long stravaSummarySegmentEffortId;
    @JsonProperty("activity_id")
    private Long activityId;
    @JsonProperty("elapsed_time")
    private Long elapsedTime;
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("start_date_local")
    private LocalDateTime startDateLocal;
    @JsonProperty("distance")
    private Float distance;
    @JsonProperty("is_kom")
    private Boolean isKom;
}
