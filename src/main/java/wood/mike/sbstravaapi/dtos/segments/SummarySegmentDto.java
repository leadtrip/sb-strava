package wood.mike.sbstravaapi.dtos.segments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SummarySegmentDto {
    @JsonProperty("id")
    private Long stravaSegmentId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("activity_type")
    private String activityType;
    @JsonProperty("distance")
    private Float distance;
    @JsonProperty("average_grade")
    private Float averageGrade;
    @JsonProperty("maximum_grade")
    private Float maximumGrade;
    @JsonProperty("elevation_high")
    private Float elevationHigh;
    @JsonProperty("elevation_low")
    private Float elevationLow;
    @JsonProperty("climb_category")
    private Integer climbCategory;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country")
    private String country;
    @JsonProperty("athlete_pr_effort")
    private SummaryPRSegmentEffortDto summaryPRSegmentEffort;
    @JsonProperty("athlete_segment_stats")
    private SummarySegmentEffortDto summarySegmentEffort;
}
