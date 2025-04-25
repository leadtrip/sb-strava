package wood.mike.sbstravaapi.clients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;

import java.util.List;

public interface StravaApiClient {

    @GetMapping("/athlete")
    ResponseEntity<AthleteDto> fetchAthlete(String accessToken);

    @GetMapping("/activities/{activityId}")
    ResponseEntity<ActivityDto> fetchActivity(@PathVariable String activityId, String accessToken);

    @GetMapping("/activities")
    ResponseEntity<List<ActivityDto>> fetchActivities(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "per_page", required = false) Integer perPage,
            String accessToken);

    @GetMapping("/activities")
    ResponseEntity<List<ActivityDto>> activitiesAfter(@RequestParam Long after, String accessToken);
}