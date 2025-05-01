package wood.mike.sbstravaapi.clients;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;

import java.util.List;

@Component
public class StravaApiClientImpl implements StravaApiClient {

    private final RestClient restClient;

    @Autowired
    public StravaApiClientImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://www.strava.com/api/v3").build();
    }

    @Override
    public ResponseEntity<AthleteDto> fetchAthlete() {
        return this.restClient.get()
                .uri("/athlete")
                .retrieve()
                .toEntity(AthleteDto.class);
    }

    @Override
    public ResponseEntity<ActivityDto> fetchActivity(String activityId) {
        return this.restClient.get()
                .uri("/activities/{activityId}", activityId)
                .retrieve()
                .toEntity(ActivityDto.class);
    }

    @Override
    public ResponseEntity<List<ActivityDto>> fetchActivities(Integer page, Integer perPage) {
        return this.restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/activities")
                        .queryParam("page", page)
                        .queryParam("per_page", perPage)
                        .build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public ResponseEntity<List<ActivityDto>> activitiesAfter(Long after) {
        return this.restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/activities")
                        .queryParam("after", after)
                        .build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }
}
