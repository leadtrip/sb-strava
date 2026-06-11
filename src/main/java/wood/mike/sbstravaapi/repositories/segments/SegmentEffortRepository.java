package wood.mike.sbstravaapi.repositories.segments;

import org.springframework.data.jpa.repository.JpaRepository;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.segments.SegmentEffort;

public interface SegmentEffortRepository extends JpaRepository<SegmentEffort, Long> {
    boolean existsByActivity(Activity activity);
}
