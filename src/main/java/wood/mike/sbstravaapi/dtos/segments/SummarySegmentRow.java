package wood.mike.sbstravaapi.dtos.segments;

import wood.mike.sbstravaapi.entities.segments.SummarySegment;
import wood.mike.sbstravaapi.utils.ActivityFormatter;

public class SummarySegmentRow {

    private final SummarySegment summarySegment;
    private final ActivityFormatter activityFormatter;

    public SummarySegmentRow(SummarySegment summarySegment,
                             ActivityFormatter activityFormatter) {
        this.summarySegment = summarySegment;
        this.activityFormatter = activityFormatter;
    }

    public String getName() {
        return summarySegment.getName();
    }

    public String getActivityType() {
        return summarySegment.getActivityType();
    }

    public String getDistance() {
        return activityFormatter.formatDistance(summarySegment.getDistance());
    }

    public String getAverageGrade() {
        return activityFormatter.roundWithUnit(summarySegment.getAverageGrade(), "%");
    }

    public String getMaximumGrade() {
        return activityFormatter.roundWithUnit(summarySegment.getMaximumGrade(), "%");
    }

    public String getElevationHigh() {
        return activityFormatter.roundWithUnit(summarySegment.getElevationHigh(), "m");
    }

    public Integer getClimbCategory() {
        return summarySegment.getClimbCategory();
    }

    public String getLocation() {
        return summarySegment.getCity() + ", " + summarySegment.getState() + ", " + summarySegment.getCountry();
    }

    public String getPrActivity() {
        if (summarySegment.getSummaryPRSegmentEffort() != null) {
            return "<a href='/activity/" + summarySegment.getSummaryPRSegmentEffort().getActivity().getStravaActivityId() + "'>" + activityFormatter.formatDateTime(summarySegment.getSummaryPRSegmentEffort().getActivity().getStartDate()) + "</a>";
        }
        return "PR for segment not found";
    }
}
