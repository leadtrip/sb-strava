package wood.mike.sbstravaapi.entities.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.time.LocalDateTime;

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
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

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

    @Column(name = "average_watts")
    private Float averageWatts;

    @Column(name = "max_watts")
    private Integer maxWatts;

    @Column(name = "weighted_average_watts")
    private Integer weightedAverageWatts;
}
