package wood.mike.sbstravaapi.transformers.segments;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;
import wood.mike.sbstravaapi.mappers.segments.SummarySegmentMapper;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Service
public class SummarySegmentTransformer {

    private final AthleteService athleteService;
    private final SummarySegmentMapper mapper;

    public SummarySegmentTransformer(AthleteService athleteService, SummarySegmentMapper mapper) {
        this.athleteService = athleteService;
        this.mapper = mapper;
    }

    public SummarySegment toEntity(SummarySegmentDto dto) {
        SummarySegment segment = mapper.toEntity(dto);
        segment.setAthlete(athleteService.getCurrentlyLoggedInAthleteOrThrow());
        return segment;
    }
}

