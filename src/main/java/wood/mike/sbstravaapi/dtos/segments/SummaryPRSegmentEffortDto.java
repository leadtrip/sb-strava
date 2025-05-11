package wood.mike.sbstravaapi.dtos.segments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wood.mike.sbstravaapi.entities.activity.Activity;

import java.time.LocalDateTime;

@Data
public class SummaryPRSegmentEffortDto {
    @JsonProperty("id")
    private Long stravaSummaryPrSegmentEffortId;
    @JsonProperty("pr_activity_id")
    private Activity activity;
    @JsonProperty("pr_elapsed_time")
    private Long prElapsedTime;
    @JsonProperty("pr_date")
    private LocalDateTime prDate;
    @JsonProperty("effort_count")
    private Integer effortCount;
}
