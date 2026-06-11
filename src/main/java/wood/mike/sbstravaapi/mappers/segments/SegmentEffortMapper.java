package wood.mike.sbstravaapi.mappers.segments;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.segments.DetailedSegmentEffortDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.segments.SegmentEffort;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.segments.SummarySegmentService;

@Mapper(componentModel = "spring")
public abstract class SegmentEffortMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activity", expression = "java(activity)")
    @Mapping(target = "athlete", expression = "java(athleteService.getCurrentlyLoggedInAthleteOrThrow())")
    @Mapping(target = "segment", expression = "java(summarySegmentService.findByStravaSegmentId(dto.getSegment().getStravaSegmentId()).orElse(null))")
    public abstract SegmentEffort toEntity(
            DetailedSegmentEffortDto dto,
            @Context Activity activity,
            @Context AthleteService athleteService,
            @Context SummarySegmentService summarySegmentService
    );
}