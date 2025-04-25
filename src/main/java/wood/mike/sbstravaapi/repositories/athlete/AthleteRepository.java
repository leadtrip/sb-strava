package wood.mike.sbstravaapi.repositories.athlete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
