package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "athlete", ignore = true)
    SummarySegment toEntity(SummarySegmentDto dto, @Context ActivityService activityService);
}

