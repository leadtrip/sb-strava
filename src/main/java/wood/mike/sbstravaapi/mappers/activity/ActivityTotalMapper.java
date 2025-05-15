package wood.mike.sbstravaapi.mappers.activity;

import org.mapstruct.Mapper;
import wood.mike.sbstravaapi.dtos.activity.ActivityTotalDto;
import wood.mike.sbstravaapi.entities.activity.ActivityTotal;

@Mapper(componentModel = "spring")
public interface ActivityTotalMapper {
    ActivityTotal toEntity(ActivityTotalDto dto);
}
