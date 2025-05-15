package wood.mike.sbstravaapi.mappers.activity;

import org.mapstruct.Mapper;
import wood.mike.sbstravaapi.dtos.activity.ActivityStatsDto;
import wood.mike.sbstravaapi.entities.activity.ActivityStats;

@Mapper(
        componentModel = "spring",
        uses = { ActivityTotalMapper.class }
)
public interface ActivityStatsMapper {
    ActivityStats toEntity(ActivityStatsDto dto);
}
