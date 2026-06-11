package wood.mike.sbstravaapi.transformers.segments;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;
import wood.mike.sbstravaapi.mappers.segments.SummarySegmentMapper;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Service
public class SummarySegmentTransformer {

    private final AthleteService athleteService;
    private final SummarySegmentMapper summarySegmentMapper;

    public SummarySegmentTransformer(
            AthleteService athleteService,
            SummarySegmentMapper summarySegmentMapper
    ) {
        this.athleteService = athleteService;
        this.summarySegmentMapper = summarySegmentMapper;
    }

    public SummarySegment toEntity(SummarySegmentDto dto) {
        SummarySegment entity = summarySegmentMapper.toEntity(dto);
        entity.setAthlete(athleteService.getCurrentlyLoggedInAthleteOrThrow());
        return entity;
    }
}



