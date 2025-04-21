package wood.mike.sbstravaapi.entities.athlete;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "athlete_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AthleteToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;
    private String accessToken;
    private String refreshToken;
    private Long expiresAt;
    private Long expiresIn;

    @Transient
    public Long getStravaAthleteId() {
        return athlete.getStravaAthleteId();
    }
}
