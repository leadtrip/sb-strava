package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;

@Mapper(
        componentModel = "spring",
        uses = {
                SummaryPRSegmentEffortMapper.class,
                SummarySegmentEffortMapper.class
        }
)
public interface SummarySegmentMapper {

    @Mapping(target = "athlete", ignore = true)
    SummarySegment toEntity(SummarySegmentDto dto);
}
