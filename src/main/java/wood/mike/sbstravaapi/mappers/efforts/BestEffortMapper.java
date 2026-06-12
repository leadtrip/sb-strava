package wood.mike.sbstravaapi.mappers.efforts;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.segments.DetailedSegmentEffortDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.efforts.BestEffort;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Mapper(componentModel = "spring")
public abstract class BestEffortMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activity", expression = "java(activity)")
    @Mapping(target = "athlete", expression = "java(athleteService.getCurrentlyLoggedInAthleteOrThrow())")
    public abstract BestEffort toEntity(
            DetailedSegmentEffortDto dto,
            @Context Activity activity,
            @Context AthleteService athleteService
    );
}
