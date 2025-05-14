package wood.mike.sbstravaapi.entities.segments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wood.mike.sbstravaapi.entities.activity.Activity;

import java.time.LocalDateTime;

@Entity
@Table(name = "summary_pr_segment_effort")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryPRSegmentEffort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strava_summary_pr_segment_effort_id", unique = true, nullable = false)
    private Long stravaSummaryPrSegmentEffortId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "elapsed_time")
    private Long elapsedTime;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "start_date_local")
    private LocalDateTime startDateLocal;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "is_kom")
    private Boolean isKom;
}
