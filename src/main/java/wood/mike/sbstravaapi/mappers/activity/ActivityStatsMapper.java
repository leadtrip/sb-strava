package wood.mike.sbstravaapi.mappers.activity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.activity.ActivityStatsDto;
import wood.mike.sbstravaapi.entities.activity.ActivityStats;

@Mapper(
        componentModel = "spring",
        uses = { ActivityTotalMapper.class }
)
public interface ActivityStatsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "athlete", ignore = true)
    ActivityStats toEntity(ActivityStatsDto dto);
}
