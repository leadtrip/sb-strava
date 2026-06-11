package wood.mike.sbstravaapi.dtos.segments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;

import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedSegmentEffortDto {

    @JsonProperty("id")
    private Long stravaSegmentEffortId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("elapsed_time")
    private Integer elapsedTime;

    @JsonProperty("moving_time")
    private Integer movingTime;

    @JsonProperty("start_date")
    private ZonedDateTime startDate;

    @JsonProperty("start_date_local")
    private ZonedDateTime startDateLocal;

    @JsonProperty("distance")
    private Float distance;

    @JsonProperty("is_kom")
    private Boolean isKom;

    @JsonProperty("start_index")
    private Integer startIndex;

    @JsonProperty("end_index")
    private Integer endIndex;

    @JsonProperty("average_cadence")
    private Float averageCadence;

    @JsonProperty("average_watts")
    private Float averageWatts;

    @JsonProperty("device_watts")
    private Boolean deviceWatts;

    @JsonProperty("average_heartrate")
    private Float averageHeartrate;

    @JsonProperty("max_heartrate")
    private Float maxHeartrate;

    @JsonProperty("kom_rank")
    private Integer komRank;

    @JsonProperty("pr_rank")
    private Integer prRank;

    @JsonProperty("hidden")
    private Boolean hidden;

    @JsonProperty("activity")
    private ActivityDto activity;

    @JsonProperty("athlete")
    private AthleteDto athlete;

    @JsonProperty("segment")
    private SummarySegmentDto segment;
}