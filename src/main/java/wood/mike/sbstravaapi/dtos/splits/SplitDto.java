package wood.mike.sbstravaapi.dtos.splits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wood.mike.sbstravaapi.entities.activity.Activity;

@Data
public class SplitDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("activity")
    private Activity activity;

    @JsonProperty("elapsed_time")
    private Integer elapsedTime;

    @JsonProperty("elevation_difference")
    private Float elevationDifference;

    @JsonProperty("moving_time")
    private Integer movingTime;

    @JsonProperty("split")
    private Integer split;

    @JsonProperty("average_speed")
    private Float averageSpeed;

    @JsonProperty("average_grade_adjusted_speed")
    private Float averageGradeAdjustedSpeed;

    @JsonProperty("average_heartrate")
    private Float averageHeartrate;

    @JsonProperty("pace_zone")
    private Integer paceZone;
}
