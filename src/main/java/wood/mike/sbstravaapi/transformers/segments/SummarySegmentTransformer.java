package wood.mike.sbstravaapi.transformers.segments;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Service
public class SummarySegmentTransformer {

    private final AthleteService athleteService;

    public SummarySegmentTransformer(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    public SummarySegment toEntity(SummarySegmentDto dto) {
        SummarySegment summarySegment = new SummarySegment();
        summarySegment.setStravaSegmentId(dto.getStravaSegmentId());
        summarySegment.setAthlete(athleteService.getCurrentlyLoggedInAthleteOrThrow());
        summarySegment.setName(dto.getName());
        summarySegment.setActivityType(dto.getActivityType());
        summarySegment.setAverageGrade(dto.getAverageGrade());
        summarySegment.setMaximumGrade(dto.getMaximumGrade());
        summarySegment.setDistance(dto.getDistance());
        summarySegment.setCountry(dto.getCountry());
        summarySegment.setCity(dto.getCity());
        summarySegment.setState(dto.getState());
        summarySegment.setAverageLow(dto.getAverageLow());
        summarySegment.setClimbCategory(dto.getClimbCategory());
        summarySegment.setSummaryPRSegmentEffort(dto.getSummaryPRSegmentEffort());
        summarySegment.setSummarySegmentEffort(dto.getSummarySegmentEffort());
        summarySegment.setElevationHigh(dto.getElevationHigh());
        summarySegment.setAverageLow(dto.getAverageLow());
        return summarySegment;
    }
}
