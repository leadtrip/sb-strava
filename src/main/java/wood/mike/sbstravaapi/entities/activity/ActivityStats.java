package wood.mike.sbstravaapi.entities.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

@Entity
@Table(name = "activity_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;
    @Column(name = "biggest_ride_distance")
    private Double biggestRideDistance;
    @Column(name = "biggest_climb_elevation_gain")
    private Double biggestClimbElevationGain;
    @JoinColumn(name = "recent_ride_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal recentRideTotals;
    @JoinColumn(name = "recent_run_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal recentRunTotals;
    @JoinColumn(name = "recent_swim_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal recentSwimTotals;
    @JoinColumn(name = "ytd_ride_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal ytdRideTotals;
    @JoinColumn(name = "ytd_run_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal ytdRunTotals;
    @JoinColumn(name = "ytd_swim_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal ytdSwimTotals;
    @JoinColumn(name = "all_ride_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal allRideTotals;
    @JoinColumn(name = "all_run_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal allRunTotals;
    @JoinColumn(name = "all_swim_totals")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ActivityTotal allSwimTotals;
}
