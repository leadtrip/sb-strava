package wood.mike.sbstravaapi.repositories.athlete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wood.mike.sbstravaapi.entities.athlete.AthleteToken;

import java.util.Optional;

@Repository
public interface AthleteTokenRepository extends JpaRepository<AthleteToken, Long> {
    Optional<AthleteToken> findByAthlete_StravaAthleteId(Long stravaAthleteId);
}
