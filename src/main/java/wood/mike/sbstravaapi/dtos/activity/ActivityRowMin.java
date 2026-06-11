package wood.mike.sbstravaapi.dtos.activity;

import lombok.Getter;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.utils.ActivityFormatter;

public class ActivityRowMin {
    @Getter
    private final String startDate;
    @Getter private final String name;
    @Getter private final String nameAsLink;
    @Getter private final String optDescription;
    @Getter private final String description;
    @Getter private final String distance;
    @Getter private final String elapsedTime;
    @Getter private final String totalElevationGain;
    @Getter private final String sportType;
    @Getter private final String averageWatts;
    @Getter private final Integer sufferScore;

    public ActivityRowMin(Activity activity, ActivityFormatter activityFormatter) {
        this.startDate = activityFormatter.formatDateTime(activity.getStartDate());
        this.name = activity.getName();
        this.optDescription = activity.getDescription() != null ? "<span class=\"act-desc\">" + activity.getDescription() + "</span>" : "";
        this.nameAsLink = "<a href='/activity/" + activity.getStravaActivityId() + "'>" + activity.getName() + "</a><br>" + optDescription;
        this.description = activity.getDescription();
        this.distance = activityFormatter.formatDistance(activity.getDistance());
        this.elapsedTime = activityFormatter.formatDuration(activity.getElapsedTime());
        this.totalElevationGain = activityFormatter.roundWithUnit(activity.getTotalElevationGain(), "m");
        this.sportType = activity.getSportType();
        this.averageWatts = activityFormatter.roundWithUnit(activity.getAverageWatts(), "w");
        this.sufferScore = activity.getSufferScore();
    }
}
