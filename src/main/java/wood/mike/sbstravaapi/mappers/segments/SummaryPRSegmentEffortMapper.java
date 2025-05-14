package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Mapper;
import wood.mike.sbstravaapi.dtos.segments.SummaryPRSegmentEffortDto;
import wood.mike.sbstravaapi.entities.segments.SummaryPRSegmentEffort;

@Mapper(componentModel = "spring")
public interface SummaryPRSegmentEffortMapper {
    SummaryPRSegmentEffort toEntity(SummaryPRSegmentEffortDto dto);
}