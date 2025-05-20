package wood.mike.sbstravaapi.mappers.activity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.activity.ActivityTotalDto;
import wood.mike.sbstravaapi.entities.activity.ActivityTotal;

@Mapper(componentModel = "spring")
public interface ActivityTotalMapper {
    @Mapping(target = "id", ignore = true)
    ActivityTotal toEntity(ActivityTotalDto dto);
}
