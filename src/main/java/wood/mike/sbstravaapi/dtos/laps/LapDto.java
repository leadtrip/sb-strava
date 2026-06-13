package wood.mike.sbstravaapi.dtos.laps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.time.LocalDateTime;

@Data
public class LapDto {

    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("activity")
    private Activity activity;

    @JsonProperty("athlete")
    private Athlete athlete;

    @JsonProperty("average_cadence")
    private Float averageCadence;

    @JsonProperty("average_speed")
    private Float averageSpeed;

    @JsonProperty("distance")
    private Float distance;

    @JsonProperty("elapsed_time")
    private Integer elapsedTime;

    @JsonProperty("start_index")
    private Integer startIndex;

    @JsonProperty("end_index")
    private Integer endIndex;

    @JsonProperty("lap_index")
    private Integer lapIndex;

    @JsonProperty("max_speed")
    private Float maxSpeed;

    @JsonProperty("moving_time")
    private Integer movingTime;

    @JsonProperty("name")
    private String name;

    @JsonProperty("pace_zone")
    private Integer paceZone;

    @JsonProperty("split")
    private Integer split;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("start_date_local")
    private LocalDateTime startDateLocal;

    @JsonProperty("total_elevation_gain")
    private Float totalElevationGain;
}
