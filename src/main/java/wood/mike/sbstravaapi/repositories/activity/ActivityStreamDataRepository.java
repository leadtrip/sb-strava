package wood.mike.sbstravaapi.repositories.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import wood.mike.sbstravaapi.entities.activity.ActivityStreamData;

public interface ActivityStreamDataRepository extends JpaRepository<ActivityStreamData, Long> {
    boolean existsByActivityId(Long activityId);
}
