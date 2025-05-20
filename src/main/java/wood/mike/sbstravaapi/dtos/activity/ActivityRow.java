package wood.mike.sbstravaapi.dtos.activity;

import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.activity.ActivityStreamData;
import wood.mike.sbstravaapi.utils.ActivityFormatter;

import java.util.Comparator;
import java.util.List;

public class ActivityRow {

    private final Activity activity;
    private final ActivityFormatter activityFormatter;

    public ActivityRow(Activity activity, ActivityFormatter activityFormatter) {
        this.activity = activity;
        this.activityFormatter = activityFormatter;
    }

    public String getStartDate() {
        return activityFormatter.formatDateTime(activity.getStartDate());
    }

    public String getName() {
        return activity.getName();
    }

    public String getNameAsLink() {
        return "<a href='/activity/" + activity.getStravaActivityId() + "'>" + activity.getName() + "</a>";
    }

    public String getDistance() {
        return activityFormatter.formatDistance(activity.getDistance());
    }

    public String getMovingTime() {
        return activityFormatter.formatDuration(activity.getMovingTime());
    }

    public String getElapsedTime() {
        return activityFormatter.formatDuration(activity.getElapsedTime());
    }

    public String getTotalElevationGain() {
        return activityFormatter.roundWithUnit(activity.getTotalElevationGain(),"m");
    }

    public String getAverageSpeed() {
        return activityFormatter.formatSpeed(activity.getAverageSpeed());
    }

    public String getMaxSpeed() {
        return activityFormatter.formatSpeed(activity.getMaxSpeed());
    }

    public String getSportType() {
        return activity.getSportType();
    }

    public String getAverageWatts() {
        return activityFormatter.roundWithUnit(activity.getAverageWatts(),"w");
    }

    public String getMaxWatts() {
        return activityFormatter.roundWithUnit(activity.getMaxWatts(),"w");
    }

    public String getKilojoules() {
        return activityFormatter.roundWithUnit(activity.getKilojoules(),"kJ");
    }

    public Integer getSufferScore() {
        return activity.getSufferScore();
    }

    public String getSummaryPolyline() {
        return activity.getPolylineMap().getSummaryPolyline();
    }

    public List<Integer> getStreamData(String streamType) {
        return activity.getStreamData().stream()
                .filter(data -> streamType.equals(data.getStreamType()))
                .sorted(Comparator.comparingInt(ActivityStreamData::getSequenceIndex))
                .map(ActivityStreamData::getValue)
                .toList();
    }

}
