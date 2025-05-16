package wood.mike.sbstravaapi.dtos.polylinemap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PolylineMapDto {
    @JsonProperty("id")
    private String stravaId;
    @JsonProperty("polyline")
    private String polyline;
    @JsonProperty("summary_polyline")
    private String summaryPolyline;
}
