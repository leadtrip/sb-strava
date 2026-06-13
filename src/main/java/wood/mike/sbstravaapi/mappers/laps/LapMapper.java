package wood.mike.sbstravaapi.mappers.laps;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wood.mike.sbstravaapi.dtos.laps.LapDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.laps.Lap;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Mapper(componentModel = "spring")
public abstract class LapMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "stravaLapId")
    @Mapping(target = "activity", expression = "java(activity)")
    @Mapping(target = "athlete", expression = "java(athleteService.getCurrentlyLoggedInAthleteOrThrow())")
    public abstract Lap toEntity(
            LapDto dto,
            @Context Activity activity,
            @Context AthleteService athleteService
    );
}
