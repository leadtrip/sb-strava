package wood.mike.sbstravaapi.entities.athlete;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "athlete")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strava_athlete_id", unique = true, nullable = false)
    private Long stravaAthleteId;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "profile_medium")
    private String profileMedium;
    @Column(name = "profile")
    private String profile;
    @Column(name = "country")
    private String country;
    @Column(name = "sex")
    private String sex;
    @Column(name = "ftp")
    private String ftp;
    @Column(name = "weight")
    private String weight;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
