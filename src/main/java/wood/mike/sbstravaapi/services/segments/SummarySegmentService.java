package wood.mike.sbstravaapi.services.segments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;
import wood.mike.sbstravaapi.repositories.segments.SummarySegmentRepository;
import wood.mike.sbstravaapi.repositories.segments.SummarySegmentSpecification;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.segments.SummarySegmentTransformer;

import java.util.List;

@Service
@Slf4j
public class SummarySegmentService {

    private final SummarySegmentRepository summarySegmentRepository;
    private final SummarySegmentTransformer summarySegmentTransformer;
    private final StravaService stravaService;
    private final AthleteService athleteService;

    public SummarySegmentService(SummarySegmentRepository summarySegmentRepository,
                                 SummarySegmentTransformer summarySegmentTransformer,
                                 StravaService stravaService,
                                 AthleteService athleteService) {
        this.summarySegmentRepository = summarySegmentRepository;
        this.summarySegmentTransformer = summarySegmentTransformer;
        this.stravaService = stravaService;
        this.athleteService = athleteService;
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

    public Page<SummarySegment> getStarredSegments(Integer page, Integer perPage, String type) {
        Athlete athlete = athleteService.getCurrentlyLoggedInAthlete().orElseThrow(() -> new RuntimeException("Athlete not found"));
        Pageable pageable = PageRequest.of(page, perPage, Sort.by("name"));
        return summarySegmentRepository.findAll(SummarySegmentSpecification.withFilters(athlete, type), pageable);
    }

    public long countAll() {
        return summarySegmentRepository.count();
    }
}
