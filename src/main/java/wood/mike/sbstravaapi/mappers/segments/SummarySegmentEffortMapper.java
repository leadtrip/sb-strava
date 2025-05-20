package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentEffortDto;
import wood.mike.sbstravaapi.entities.segments.SummarySegmentEffort;

@Mapper(componentModel = "spring")
public interface SummarySegmentEffortMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activity", ignore = true)
    SummarySegmentEffort toEntity(SummarySegmentEffortDto dto);
}
