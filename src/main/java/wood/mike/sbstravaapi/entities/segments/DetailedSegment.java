package wood.mike.sbstravaapi.entities.segments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detailed_segment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailedSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
