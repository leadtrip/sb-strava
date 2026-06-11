package wood.mike.sbstravaapi.dtos.activity;

import lombok.Getter;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.activity.ActivityStreamData;
import wood.mike.sbstravaapi.entities.segments.SegmentEffort;
import wood.mike.sbstravaapi.utils.ActivityFormatter;

import java.util.Comparator;
import java.util.List;

public class ActivityRow {
    @Getter private final String startDate;
    @Getter private final String name;
    @Getter private final String nameAsLink;
    @Getter private final String optDescription;
    @Getter private final String description;
    @Getter private final String distance;
    @Getter private final String movingTime;
    @Getter private final String elapsedTime;
    @Getter private final String totalElevationGain;
    @Getter private final String averageSpeed;
    @Getter private final String maxSpeed;
    @Getter private final String sportType;
    @Getter private final String averageWatts;
    @Getter private final String maxWatts;
    @Getter private final String kilojoules;
    @Getter private final String calories;
    @Getter private final Integer sufferScore;
    @Getter private final String deviceName;
    @Getter private final String summaryPolyline;
    @Getter private final List<SegmentEffort> segmentEfforts;
    private final List<ActivityStreamData> streamData;

    public ActivityRow(Activity activity, ActivityFormatter activityFormatter) {
        this.startDate = activityFormatter.formatDateTime(activity.getStartDate());
        this.name = activity.getName();
        this.optDescription = activity.getDescription() != null ? "<span class=\"act-desc\">" + activity.getDescription() + "</span>" : "";
        this.nameAsLink = "<a href='/activity/" + activity.getStravaActivityId() + "'>" + activity.getName() + "</a><br>" + optDescription;
        this.description = activity.getDescription();
        this.distance = activityFormatter.formatDistance(activity.getDistance());
        this.movingTime = activityFormatter.formatDuration(activity.getMovingTime());
        this.elapsedTime = activityFormatter.formatDuration(activity.getElapsedTime());
        this.totalElevationGain = activityFormatter.roundWithUnit(activity.getTotalElevationGain(), "m");
        this.averageSpeed = activityFormatter.formatSpeed(activity.getAverageSpeed());
        this.maxSpeed = activityFormatter.formatSpeed(activity.getMaxSpeed());
        this.sportType = activity.getSportType();
        this.averageWatts = activityFormatter.roundWithUnit(activity.getAverageWatts(), "w");
        this.maxWatts = activityFormatter.roundWithUnit(activity.getMaxWatts(), "w");
        this.kilojoules = activityFormatter.roundWithUnit(activity.getKilojoules(), "kJ");
        Float caloriesValue = activity.getCalories() != null ? activity.getCalories() : activity.getKilojoules() != null ? activity.getKilojoules() : 0;
        this.calories = activityFormatter.round(caloriesValue);
        this.sufferScore = activity.getSufferScore();
        this.deviceName = activity.getDeviceName();
        this.summaryPolyline = activity.getPolylineMap().getSummaryPolyline();
        this.streamData = activity.getStreamData();
        this.segmentEfforts = activity.getSegmentEfforts();
    }

    public List<Integer> getStreamData(String streamType) {
        return streamData.stream()
                .filter(data -> streamType.equals(data.getStreamType()))
                .sorted(Comparator.comparingInt(ActivityStreamData::getSequenceIndex))
                .map(ActivityStreamData::getValue)
                .toList();
    }

    public StreamAnalysisData getStreamAnalysisData(String streamType) {
        List<Integer> streamData = getStreamData(streamType);
        Integer max = streamData.stream().max(Integer::compareTo).orElse(0);
        Integer average = streamData.isEmpty() ? 0 : streamData.stream().reduce(0, Integer::sum) / streamData.size();
        return new StreamAnalysisData(streamData, max, average);
    }

    public record StreamAnalysisData(List<Integer> streamData, Integer max, Integer average) { }
}