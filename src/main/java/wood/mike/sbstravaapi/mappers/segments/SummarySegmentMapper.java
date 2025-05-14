package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;
import wood.mike.sbstravaapi.services.activity.ActivityService;

@Mapper(
        componentModel = "spring",
        uses =
                {
                        SummaryPRSegmentEffortMapper.class,
                        SummarySegmentEffortMapper.class
                })
public interface SummarySegmentMapper {
    SummarySegment toEntity(SummarySegmentDto dto, @Context ActivityService activityService);
}

