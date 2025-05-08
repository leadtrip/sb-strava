package wood.mike.sbstravaapi.dtos.activity;

import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.utils.ActivityFormatter;

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
        return "<a href='/activity/" + activity.getStravaActivityId() + "'>" + activity.getName() + "</a>";
    }

    public String getDistance() {
        return activityFormatter.formatDistance(activity.getDistance());
    }

    public String getElapsedTime() {
        return activityFormatter.formatDuration(activity.getElapsedTime());
    }

    public String getTotalElevationGain() {
        return activityFormatter.roundWithUnit(activity.getTotalElevationGain(),"m");
    }

    public String getSportType() {
        return activity.getSportType();
    }

    public String getAverageWatts() {
        return activityFormatter.roundWithUnit(activity.getAverageWatts(),"w");
    }

    public Integer getSufferScore() {
        return activity.getSufferScore();
    }
}
