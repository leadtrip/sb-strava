package wood.mike.sbstravaapi.entities.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity_stream_data",
        uniqueConstraints = @UniqueConstraint(columnNames = {"activity_id", "stream_type", "sequence_index"}))
public class ActivityStreamData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(name = "stream_type", nullable = false)
    private String streamType;

    @Column(name = "sequence_index", nullable = false)
    private int sequenceIndex;

    @Column(name = "value", nullable = false)
    private int value;
}

