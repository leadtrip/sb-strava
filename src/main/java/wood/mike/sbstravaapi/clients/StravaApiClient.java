package wood.mike.sbstravaapi.clients;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.activity.ActivityStatsDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentDto;

import java.util.List;
import java.util.Optional;

public interface StravaApiClient {

    @GetMapping("/token")
    ResponseEntity<AthleteTokenDto> fetchAthleteToken(@RequestParam("code") String authorizationCode);

    @GetMapping("/refreshtoken")
    ResponseEntity<AthleteTokenDto> refreshAthleteToken(@RequestParam("refresh_token") String refreshToken);

    @GetMapping("/athlete")
    ResponseEntity<AthleteDto> fetchAthlete();

    @GetMapping("/athlete/{id}/stats")
    ResponseEntity<ActivityStatsDto> fetchAthleteStats(Long athleteId);

    @GetMapping("/activities/{activityId}")
    ResponseEntity<ActivityDto> fetchActivity(@PathVariable String activityId);

    @GetMapping("/activities")
    ResponseEntity<List<ActivityDto>> fetchActivities(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "per_page", required = false) Integer perPage);

    @GetMapping("/activities")
    ResponseEntity<List<ActivityDto>> activitiesAfter(@RequestParam Long after);

    @GetMapping("/activities")
    ResponseEntity<List<ActivityDto>> activitiesBefore(@RequestParam Long before);

    @GetMapping("/segments/starred")
    ResponseEntity<List<SummarySegmentDto>> fetchStarredSegments(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "per_page", required = false) Integer perPage);

    @GetMapping("/activities/{activityId}/streams")
    Optional<JsonNode> fetchActivityStreams(
            @PathVariable String activityId,
            @RequestParam(value = "keys", required = false) List<String> keys);
}