package wood.mike.sbstravaapi.entities.splits;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wood.mike.sbstravaapi.entities.activity.Activity;

@Entity
@Table(name = "split")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Split {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "elapsed_time")
    private Integer elapsedTime;

    @Column(name = "elevation_difference")
    private Float elevationDifference;

    @Column(name = "moving_time")
    private Integer movingTime;

    @Column(name = "split")
    private Integer split;

    @Column(name = "average_speed")
    private Float averageSpeed;

    @Column(name = "average_grade_adjusted_speed")
    private Float averageGradeAdjustedSpeed;

    @Column(name = "average_heartrate")
    private Float averageHeartrate;

    @Column(name = "pace_zone")
    private Integer paceZone;
}
