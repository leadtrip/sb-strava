package wood.mike.sbstravaapi.repositories.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {
    List<Activity> findByAthleteOrderByStartDateDesc(Athlete athlete, Pageable pageable);
    Optional<Activity> findByStravaActivityId(Long stravaActivityId);
    boolean existsByStravaActivityId(Long stravaActivityId);
    Page<Activity> findByAthlete(Athlete athlete, Pageable pageable);
    Optional<Activity> findFirstByAthleteAndSourceOrderByStartDateAsc(
            Athlete athlete,
            ActivitySource source
    );

    @Query("SELECT NEW wood.mike.sbstravaapi.repositories.activity.WeeklyStatistic(" +
            "YEAR(a.startDate), WEEK(a.startDate), SUM(a.sufferScore)) " +
            "FROM Activity a " +
            "WHERE a.athlete = :athlete " +
            "AND (:fromDate IS NULL OR a.startDate >= :fromDate) " +
            "AND (:toDate IS NULL OR a.startDate <= :toDate) " +
            "AND (:activityType IS NULL OR :activityType = '' OR a.sportType = :activityType) " +
            "GROUP BY YEAR(a.startDate), WEEK(a.startDate) " +
            "ORDER BY YEAR(a.startDate), WEEK(a.startDate)")
    List<WeeklyStatistic> sumSufferScoreByWeek(
            @Param("athlete") Athlete athlete,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("activityType") String activityType);

    @Query("SELECT NEW wood.mike.sbstravaapi.repositories.activity.WeeklyStatistic(" +
            "YEAR(a.startDate), WEEK(a.startDate), SUM(a.distance)) " +
            "FROM Activity a " +
            "WHERE a.athlete = :athlete " +
            "AND (:fromDate IS NULL OR a.startDate >= :fromDate) " +
            "AND (:toDate IS NULL OR a.startDate <= :toDate) " +
            "AND (:activityType IS NULL OR :activityType = '' OR a.sportType = :activityType) " +
            "GROUP BY YEAR(a.startDate), WEEK(a.startDate) " +
            "ORDER BY YEAR(a.startDate), WEEK(a.startDate)")
    List<WeeklyStatistic> sumDistanceByWeek(@Param("athlete") Athlete athlete,
                                            @Param("fromDate") LocalDateTime fromDate,
                                            @Param("toDate") LocalDateTime toDate,
                                            @Param("activityType") String activityType);


}
