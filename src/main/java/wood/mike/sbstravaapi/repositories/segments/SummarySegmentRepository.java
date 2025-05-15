package wood.mike.sbstravaapi.repositories.segments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;

@Repository
public interface SummarySegmentRepository extends JpaRepository<SummarySegment, Long>, JpaSpecificationExecutor<SummarySegment> {
    boolean  existsByStravaSegmentId(Long stravaSegmentId);
}
