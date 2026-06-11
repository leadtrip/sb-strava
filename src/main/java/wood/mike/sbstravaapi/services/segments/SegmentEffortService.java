package wood.mike.sbstravaapi.services.segments;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.repositories.segments.SegmentEffortRepository;

@Service
public class SegmentEffortService {

    private final SegmentEffortRepository segmentEffortRepository;

    public SegmentEffortService(SegmentEffortRepository segmentEffortRepository) {
        this.segmentEffortRepository = segmentEffortRepository;
    }

    public boolean existsByActivity (Activity activity) {
        return segmentEffortRepository.existsByActivity(activity);
    }
}
