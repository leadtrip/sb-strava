package wood.mike.sbstravaapi.entities.laps;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.time.LocalDateTime;

@Entity
@Table(name = "lap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strava_lap_id", length = 100)
    private Long stravaLapId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    @Column(name = "average_cadence")
    private Float averageCadence;

    @Column(name = "average_speed")
    private Float averageSpeed;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "elapsed_time")
    private Integer elapsedTime;

    @Column(name = "start_index")
    private Integer startIndex;

    @Column(name = "end_index")
    private Integer endIndex;

    @Column(name = "lap_index")
    private Integer lapIndex;

    @Column(name = "max_speed")
    private Float maxSpeed;

    @Column(name = "moving_time")
    private Integer movingTime;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "pace_zone")
    private Integer paceZone;

    @Column(name = "split")
    private Integer split;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "start_date_local")
    private LocalDateTime startDateLocal;

    @Column(name = "total_elevation_gain")
    private Float totalElevationGain;
}
