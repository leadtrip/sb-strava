package wood.mike.sbstravaapi.dtos.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;
import wood.mike.sbstravaapi.dtos.laps.LapDto;
import wood.mike.sbstravaapi.dtos.polylinemap.PolylineMapDto;
import wood.mike.sbstravaapi.dtos.segments.DetailedSegmentEffortDto;
import wood.mike.sbstravaapi.repositories.activity.ActivitySource;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ActivityDto {

    @JsonProperty("id")
    private Long id;

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

    @JsonProperty("start_date_local")
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

    @JsonProperty("calories")
    private Float calories;

    @JsonProperty("average_watts")
    private Float averageWatts;

    @JsonProperty("max_watts")
    private Integer maxWatts;

    @JsonProperty("weighted_average_watts")
    private Integer weightedAverageWatts;

    @JsonProperty("suffer_score")
    private Integer sufferScore;

    @JsonProperty("device_name")
    private String deviceName;

    @JsonProperty("map")
    private PolylineMapDto map;

    @JsonProperty("source")
    private ActivitySource source;

    @JsonProperty("description")
    private String description;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("upload_id")
    private Long uploadId;

    @JsonProperty("achievement_count")
    private Integer achievementCount;

    @JsonProperty("kudos_count")
    private Integer kudosCount;

    @JsonProperty("comment_count")
    private Integer commentCount;

    @JsonProperty("athlete_count")
    private Integer athleteCount;

    @JsonProperty("photo_count")
    private Integer photoCount;

    @JsonProperty("total_photo_count")
    private Integer totalPhotoCount;

    @JsonProperty("trainer")
    private Boolean trainer;

    @JsonProperty("commute")
    private Boolean commute;

    @JsonProperty("manual")
    private Boolean manual;

    @JsonProperty("private")
    private Boolean isPrivate;

    @JsonProperty("flagged")
    private Boolean flagged;

    @JsonProperty("has_kudoed")
    private Boolean hasKudoed;

    @JsonProperty("hide_from_home")
    private Boolean hideFromHome;

    @JsonProperty("device_watts")
    private Boolean deviceWatts;

    @JsonProperty("workout_type")
    private Integer workoutType;

    @JsonProperty("upload_id_str")
    private String uploadIdStr;

    @JsonProperty("embed_token")
    private String embedToken;

    @JsonProperty("segment_efforts")
    private List<DetailedSegmentEffortDto> segmentEfforts;

    @JsonProperty("best_efforts")
    private List<DetailedSegmentEffortDto> bestEfforts;

    @JsonProperty("laps")
    private List<LapDto> laps;
}
