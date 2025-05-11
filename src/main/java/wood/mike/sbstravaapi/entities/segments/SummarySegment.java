package wood.mike.sbstravaapi.entities.segments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "summary_segment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummarySegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strava_segment_id", unique = true, nullable = false)
    private Long stravaSegmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "average_grade")
    private Float averageGrade;

    @Column(name = "maximum_grade")
    private Float maximumGrade;

    @Column(name = "elevation_high")
    private Float elevationHigh;

    @Column(name = "average_low")
    private Float averageLow;

    @Column(name = "climb_category")
    private Integer climbCategory;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "summary_pr_segment_effort_id")
    private SummaryPRSegmentEffort summaryPRSegmentEffort;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "summary_segment_effort_id")
    private SummarySegmentEffort summarySegmentEffort;
}
