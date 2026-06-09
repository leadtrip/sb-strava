package wood.mike.sbstravaapi.entities.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.entities.polylinemap.PolylineMap;
import wood.mike.sbstravaapi.repositories.activity.ActivitySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    @Column(name = "strava_activity_id", nullable = false, length = 100)
    private Long stravaActivityId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "distance", nullable = false)
    private Float distance;

    @Column(name = "moving_time", nullable = false)
    private Integer movingTime;

    @Column(name = "elapsed_time", nullable = false)
    private Integer elapsedTime;

    @Column(name = "total_elevation_gain")
    private Float totalElevationGain;

    @Column(name = "elev_high")
    private Float elevHigh;

    @Column(name = "elev_low")
    private Float elevLow;

    @Column(name = "sport_type", nullable = false, length = 50)
    private String sportType;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "timezone", nullable = false, length = 100)
    private String timezone;

    @Column(name = "average_speed")
    private Float averageSpeed;

    @Column(name = "max_speed")
    private Float maxSpeed;

    @Column(name = "gear_id", length = 50)
    private String gearId;

    @Column(name = "kilojoules")
    private Float kilojoules;

    @Column(name = "calories")
    private Float calories;

    @Column(name = "average_watts")
    private Float averageWatts;

    @Column(name = "max_watts")
    private Integer maxWatts;

    @Column(name = "weighted_average_watts")
    private Integer weightedAverageWatts;

    @Column(name = "suffer_score")
    private Integer sufferScore;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "source", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivitySource source = ActivitySource.SYNC;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "polyline_map_id")
    private PolylineMap polylineMap;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityStreamData> streamData = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @Column(name = "external_id", length = 100)
    private String externalId;

    @Column(name = "upload_id")
    private Long uploadId;

    @Column(name = "achievement_count")
    private Integer achievementCount;

    @Column(name = "kudos_count")
    private Integer kudosCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "athlete_count")
    private Integer athleteCount;

    @Column(name = "photo_count")
    private Integer photoCount;

    @Column(name = "total_photo_count")
    private Integer totalPhotoCount;

    @Column(name = "trainer")
    private Boolean trainer;

    @Column(name = "commute")
    private Boolean commute;

    @Column(name = "`manual`")
    private Boolean manual;

    @Column(name = "`private`")
    private Boolean isPrivate;

    @Column(name = "flagged")
    private Boolean flagged;

    @Column(name = "has_kudoed")
    private Boolean hasKudoed;

    @Column(name = "hide_from_home")
    private Boolean hideFromHome;

    @Column(name = "device_watts")
    private Boolean deviceWatts;

    @Column(name = "workout_type")
    private Integer workoutType;

    @Column(name = "upload_id_str", length = 100)
    private String uploadIdStr;

    @Column(name = "embed_token", length = 100)
    private String embedToken;
}
