package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.segments.SummaryPRSegmentEffortDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.segments.SummaryPRSegmentEffort;
import wood.mike.sbstravaapi.services.activity.ActivityService;

@Mapper(componentModel = "spring")
public abstract class SummaryPRSegmentEffortMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "activityId", target = "activity")
    public abstract SummaryPRSegmentEffort toEntity(
            SummaryPRSegmentEffortDto dto,
            @Context ActivityService activityService
    );

    protected Activity map(Long activityId, @Context ActivityService activityService) {
        return activityId == null ? null : activityService.getActivity(activityId);
    }
}

