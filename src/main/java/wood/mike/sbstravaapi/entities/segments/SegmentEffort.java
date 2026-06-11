package wood.mike.sbstravaapi.entities.segments;

import jakarta.persistence.*;
import lombok.*;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.time.LocalDateTime;

@Entity
@Table(name = "segment_effort")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SegmentEffort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strava_segment_effort_id", unique = true, nullable = false)
    private Long stravaSegmentEffortId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "segment_id")
    private SummarySegment segment;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "elapsed_time", nullable = false)
    private Integer elapsedTime;

    @Column(name = "moving_time", nullable = false)
    private Integer movingTime;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "start_date_local", nullable = false)
    private LocalDateTime startDateLocal;

    @Column(name = "distance", nullable = false)
    private Float distance;

    @Column(name = "is_kom")
    private Boolean isKom;

    @Column(name = "start_index")
    private Integer startIndex;

    @Column(name = "end_index")
    private Integer endIndex;

    @Column(name = "average_cadence")
    private Float averageCadence;

    @Column(name = "average_watts")
    private Float averageWatts;

    @Column(name = "device_watts")
    private Boolean deviceWatts;

    @Column(name = "average_heartrate")
    private Float averageHeartrate;

    @Column(name = "max_heartrate")
    private Float maxHeartrate;

    @Column(name = "kom_rank")
    private Integer komRank;

    @Column(name = "pr_rank")
    private Integer prRank;

    @Column(name = "hidden")
    private Boolean hidden;
}
