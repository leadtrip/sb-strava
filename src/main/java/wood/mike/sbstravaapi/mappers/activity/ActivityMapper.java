package wood.mike.sbstravaapi.mappers.activity;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.efforts.BestEffort;
import wood.mike.sbstravaapi.entities.segments.SegmentEffort;
import wood.mike.sbstravaapi.mappers.efforts.BestEffortMapper;
import wood.mike.sbstravaapi.mappers.polylinemap.PolylineMapMapper;
import wood.mike.sbstravaapi.mappers.segments.SegmentEffortMapper;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.segments.SummarySegmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {
                PolylineMapMapper.class,
                SegmentEffortMapper.class
        }
)
@Slf4j
public abstract class ActivityMapper {

    @Autowired
    protected SegmentEffortMapper segmentEffortMapper;
    @Autowired
    private BestEffortMapper bestEffortMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(source = "id", target = "stravaActivityId")
    @Mapping(target = "polylineMap.id", ignore = true)
    @Mapping(source = "map", target = "polylineMap")
    @Mapping(target = "streamData", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "athlete.id", ignore = true)
    @Mapping(target = "segmentEffortsFetched", ignore = true)
    @Mapping(target = "segmentEfforts", ignore = true)
    @Mapping(target = "bestEfforts", ignore = true)
    public abstract Activity toEntity(
            ActivityDto dto,
            @Context AthleteService athleteService,
            @Context SummarySegmentService summarySegmentService
    );

    @AfterMapping
    protected void mapEfforts(
            ActivityDto dto,
            @MappingTarget Activity activity,
            @Context AthleteService athleteService,
            @Context SummarySegmentService summarySegmentService
    ) {
        if (dto.getSegmentEfforts() != null) {
            log.info("Mapping {} segment efforts for Strava activity {}", dto.getSegmentEfforts().size(), dto.getId());
            mapSegmentEfforts(dto, activity, athleteService, summarySegmentService);
        }
        if(dto.getBestEfforts() != null) {
            log.info("Mapping {} best efforts for Strava activity {}", dto.getBestEfforts().size(), dto.getId());
            mapBestEfforts(dto, activity, athleteService);
        }
    }

    protected void mapSegmentEfforts(
            ActivityDto dto,
            @MappingTarget Activity activity,
            @Context AthleteService athleteService,
            @Context SummarySegmentService summarySegmentService
    ) {
        List<SegmentEffort> efforts = dto.getSegmentEfforts().stream()
                .map(effortDto -> segmentEffortMapper.toEntity(
                        effortDto,
                        activity,
                        athleteService,
                        summarySegmentService
                ))
                .collect(Collectors.toCollection(ArrayList::new));

        if (activity.getSegmentEfforts() == null) {
            activity.setSegmentEfforts(efforts);
        } else {
            activity.getSegmentEfforts().clear();
            activity.getSegmentEfforts().addAll(efforts);
        }
    }

    private void mapBestEfforts(
            ActivityDto dto,
            @MappingTarget Activity activity,
            @Context AthleteService athleteService
    ) {
        List<BestEffort> bestEfforts = dto.getBestEfforts().stream()
                .map(effortDto -> bestEffortMapper.toEntity(
                        effortDto,
                        activity,
                        athleteService
                ))
                .collect(Collectors.toCollection(ArrayList::new));

        if (activity.getBestEfforts() == null) {
            activity.setBestEfforts(bestEfforts);
        } else {
            activity.getBestEfforts().clear();
            activity.getBestEfforts().addAll(bestEfforts);
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "athlete", ignore = true)
    @Mapping(source = "id", target = "stravaActivityId")
    @Mapping(target = "polylineMap.id", ignore = true)
    @Mapping(source = "map", target = "polylineMap")
    @Mapping(target = "streamData", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "segmentEffortsFetched", ignore = true)
    @Mapping(target = "segmentEfforts", ignore = true)
    @Mapping(target = "bestEfforts", ignore = true)
    public abstract void updateActivityFromDto(
            ActivityDto dto,
            @MappingTarget Activity entity,
            @Context AthleteService athleteService,
            @Context SummarySegmentService summarySegmentService
    );
}
