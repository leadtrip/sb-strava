package wood.mike.sbstravaapi.repositories.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByAthleteOrderByStartDateDesc(Athlete athlete, Pageable pageable);
    Optional<Activity> findByStravaActivityId(Long stravaActivityId);
    boolean existsByStravaActivityId(Long stravaActivityId);
    Long countByAthlete(Athlete athlete);
    Page<Activity> findByAthlete(Athlete athlete, Pageable pageable);

    @Query("SELECT NEW wood.mike.sbstravaapi.repositories.activity.WeeklySufferScore(YEAR(a.startDate), WEEK(a.startDate), SUM(a.sufferScore)) " +
            "FROM Activity a " +
            "WHERE a.athlete = :athlete " +
            "GROUP BY YEAR(a.startDate), WEEK(a.startDate) " +
            "ORDER BY YEAR(a.startDate), WEEK(a.startDate)")
    List<WeeklySufferScore> sumSufferScoreByWeek(@Param("athlete") Athlete athlete);


}
