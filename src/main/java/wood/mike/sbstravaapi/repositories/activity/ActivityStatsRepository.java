package wood.mike.sbstravaapi.repositories.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wood.mike.sbstravaapi.entities.activity.ActivityStats;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.util.Optional;

@Repository
public interface ActivityStatsRepository extends JpaRepository<ActivityStats, Long> {
    Optional<ActivityStats> findByAthlete(Athlete athlete);
}
