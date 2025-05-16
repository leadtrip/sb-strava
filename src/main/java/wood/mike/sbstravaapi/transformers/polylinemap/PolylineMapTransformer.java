package wood.mike.sbstravaapi.transformers.polylinemap;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.polylinemap.PolylineMapDto;
import wood.mike.sbstravaapi.entities.polylinemap.PolylineMap;
import wood.mike.sbstravaapi.mappers.polylinemap.PolylineMapMapper;

@Service
public class PolylineMapTransformer {
    private final PolylineMapMapper polylineMapMapper;

    public PolylineMapTransformer(PolylineMapMapper polylineMapMapper) {
        this.polylineMapMapper = polylineMapMapper;
    }

    public PolylineMap toEntity(PolylineMapDto dto) {
        return polylineMapMapper.toEntity(dto);
    }
}
