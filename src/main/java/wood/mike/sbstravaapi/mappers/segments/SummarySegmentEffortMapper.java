package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Mapper;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentEffortDto;
import wood.mike.sbstravaapi.entities.segments.SummarySegmentEffort;

@Mapper(componentModel = "spring")
public interface SummarySegmentEffortMapper {
    SummarySegmentEffort toEntity(SummarySegmentEffortDto dto);
}
