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
    @JoinColumn(name = "pr_activity_id")
    private Activity activity;

    @Column(name = "pr_elapsed_time")
    private Long prElapsedTime;

    @Column(name = "pr_date")
    private LocalDateTime prDate;

    @Column(name = "effort_count")
    private Integer effortCount;
}
