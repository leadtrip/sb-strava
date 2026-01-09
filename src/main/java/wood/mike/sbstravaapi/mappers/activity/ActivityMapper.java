package wood.mike.sbstravaapi.mappers.activity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.mappers.polylinemap.PolylineMapMapper;

@Mapper(
        componentModel = "spring",
        uses = { PolylineMapMapper.class }
)
public interface ActivityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(source = "id", target = "stravaActivityId")
    @Mapping(target = "polylineMap.id", ignore = true)
    @Mapping(source = "map", target = "polylineMap")
    @Mapping(target = "streamData", ignore = true)
    @Mapping(target = "source", ignore = true)
    Activity toEntity(ActivityDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "athlete", ignore = true)
    @Mapping(source = "id", target = "stravaActivityId")
    @Mapping(target = "polylineMap.id", ignore = true)
    @Mapping(source = "map", target = "polylineMap")
    @Mapping(target = "streamData", ignore = true)
    @Mapping(target = "source", ignore = true)
    void updateActivityFromDto(ActivityDto dto, @MappingTarget Activity entity);
}
