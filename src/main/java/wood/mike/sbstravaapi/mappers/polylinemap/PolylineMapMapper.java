package wood.mike.sbstravaapi.mappers.polylinemap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.polylinemap.PolylineMapDto;
import wood.mike.sbstravaapi.entities.polylinemap.PolylineMap;

@Mapper(componentModel = "spring")
public interface PolylineMapMapper {
    @Mapping(target = "id", ignore = true)
    PolylineMap toEntity(PolylineMapDto dto);
}
