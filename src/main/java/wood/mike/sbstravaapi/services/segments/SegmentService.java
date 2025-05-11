package wood.mike.sbstravaapi.services.segments;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;
import wood.mike.sbstravaapi.repositories.segments.SummarySegmentRepository;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.segments.SummarySegmentTransformer;

import java.util.List;

@Service
public class SegmentService {

    private final SummarySegmentRepository summarySegmentRepository;
    private final SummarySegmentTransformer summarySegmentTransformer;
    private final StravaService stravaService;

    public SegmentService(SummarySegmentRepository summarySegmentRepository,
                          SummarySegmentTransformer summarySegmentTransformer,
                          StravaService stravaService) {
        this.summarySegmentRepository = summarySegmentRepository;
        this.summarySegmentTransformer = summarySegmentTransformer;
        this.stravaService = stravaService;
    }

    public Integer syncStarredSegments(Integer page, Integer perPage) {
        List<SummarySegmentDto> summarySegments = stravaService.getSummarySegments(page, perPage);
        int totalSynced = 0;
        for(SummarySegmentDto summarySegment : summarySegments) {
            if (!summarySegmentRepository.existsByStravaSegmentId(summarySegment.getStravaSegmentId())) {
                summarySegmentRepository.save(summarySegmentTransformer.toEntity(summarySegment));
                totalSynced++;
            }
        }
        return totalSynced;
    }
}
