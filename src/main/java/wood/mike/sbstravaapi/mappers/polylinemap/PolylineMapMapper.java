package wood.mike.sbstravaapi.mappers.polylinemap;

import org.mapstruct.Mapper;
import wood.mike.sbstravaapi.dtos.polylinemap.PolylineMapDto;
import wood.mike.sbstravaapi.entities.polylinemap.PolylineMap;

@Mapper(componentModel = "spring")
public interface PolylineMapMapper {
    PolylineMap toEntity(PolylineMapDto dto);
}
