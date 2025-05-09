package wood.mike.sbstravaapi.dtos.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ActivityStatsDto {
    @JsonProperty("biggest_ride_distance")
    private Double biggestRideDistance;
    @JsonProperty("biggest_climb_elevation_gain")
    private Double biggestClimbElevationGain;
    @JsonProperty("recent_ride_totals")
    private ActivityTotalDto recentRideTotals;
    @JsonProperty("recent_run_totals")
    private ActivityTotalDto recentRunTotals;
    @JsonProperty("recent_swim_totals")
    private ActivityTotalDto recentSwimTotals;
    @JsonProperty("ytd_ride_totals")
    private ActivityTotalDto ytdRideTotals;
    @JsonProperty("ytd_run_totals")
    private ActivityTotalDto ytdRunTotals;
    @JsonProperty("ytd_swim_totals")
    private ActivityTotalDto ytdSwimTotals;
    @JsonProperty("all_ride_totals")
    private ActivityTotalDto allRideTotals;
    @JsonProperty("all_run_totals")
    private ActivityTotalDto allRunTotals;
    @JsonProperty("all_swim_totals")
    private ActivityTotalDto allSwimTotals;
}
