package wood.mike.sbstravaapi.entities.polylinemap;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "polyline_map")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolylineMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "strava_id")
    private String stravaId;
    @Column(name = "polyline")
    private String polyline;
    @Column(name = "summary_polyline")
    private String summaryPolyline;
}
