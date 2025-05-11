package wood.mike.sbstravaapi.repositories.segments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;

@Repository
public interface SummarySegmentRepository extends JpaRepository<SummarySegment, Long> {
    boolean  existsByStravaSegmentId(Long stravaSegmentId);
}
