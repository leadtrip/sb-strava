package wood.mike.sbstravaapi.dtos.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ActivityTotalDto {
    private Integer count;
    private Double distance;
    @JsonProperty("moving_time")
    private Double movingTime;
    @JsonProperty("elapsed_time")
    private Double elapsedTime;
    @JsonProperty("elevation_gain")
    private Double elevationGain;
    @JsonProperty("achievement_count")
    private Integer achievementCount;
}
