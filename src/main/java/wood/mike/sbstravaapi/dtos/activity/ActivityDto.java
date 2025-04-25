package wood.mike.sbstravaapi.dtos.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;

import java.time.LocalDateTime;

@Data
public class ActivityDto {
    @JsonProperty("athlete")
    private AthleteDto athlete;
    @JsonProperty("name")
    private String name;
    @JsonProperty("distance")
    private Float distance;
    @JsonProperty("moving_time")
    private Integer movingTime;
    @JsonProperty("elapsed_time")
    private Integer elapsedTime;
    @JsonProperty("total_elevation_gain")
    private Float totalElevationGain;
    @JsonProperty("elev_high")
    private Float elevHigh;
    @JsonProperty("elev_low")
    private Float elevLow;
    @JsonProperty("sport_type")
    private String sportType;
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("average_speed")
    private Float averageSpeed;
    @JsonProperty("max_speed")
    private Float maxSpeed;
    @JsonProperty("gear_id")
    private String gearId;
    @JsonProperty("kilojoules")
    private Float kilojoules;
    @JsonProperty("average_watts")
    private Float averageWatts;
    @JsonProperty("max_watts")
    private Integer maxWatts;
    @JsonProperty("weighted_average_watts")
    private Integer weightedAverageWatts;
}
