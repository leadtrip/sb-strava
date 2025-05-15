package wood.mike.sbstravaapi.entities.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "activity_total")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer count;
    private Double distance;
    @Column(name = "moving_time")
    private Double movingTime;
    @Column(name = "elapsed_time")
    private Double elapsedTime;
    @Column(name = "elevation_gain")
    private Double elevationGain;
    @Column(name = "achievement_count")
    private Integer achievementCount;
}
