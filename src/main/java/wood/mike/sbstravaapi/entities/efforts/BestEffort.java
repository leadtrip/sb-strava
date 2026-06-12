package wood.mike.sbstravaapi.entities.efforts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.time.LocalDateTime;

@Entity
@Table(name = "best_effort")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestEffort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strava_best_effort_id", unique = true, nullable = false)
    private Long stravaSegmentEffortId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

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

    @Column(name = "pr_rank")
    private Integer prRank;

    @Column(name = "start_index")
    private Integer startIndex;

    @Column(name = "end_index")
    private Integer endIndex;
}
