package wood.mike.sbstravaapi.mappers.splits;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.splits.SplitDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.splits.Split;

@Mapper(componentModel = "spring")
public abstract class SplitMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activity", expression = "java(activity)")
    public abstract Split toEntity(
            SplitDto dto,
            @Context Activity activity
    );
}
