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
    public ResponseEntity<AthleteDto> fetchAthlete(String accessToken) {
        return this.restClient.get()
                .uri("/athlete")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(AthleteDto.class);
    }

    @Override
    public ResponseEntity<ActivityDto> fetchActivity(String activityId, String accessToken) {
        return this.restClient.get()
                .uri("/activities/{activityId}", activityId)
                .header("Authorization", "Bearer " + accessToken) // Add header here
                .retrieve()
                .toEntity(ActivityDto.class);
    }

    @Override
    public ResponseEntity<List<ActivityDto>> fetchActivities(Integer page, Integer perPage, String accessToken) {
        return this.restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/activities")
                        .queryParam("page", page)
                        .queryParam("per_page", perPage)
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public ResponseEntity<List<ActivityDto>> activitiesAfter(Long after, String accessToken) {
        return this.restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/activities")
                        .queryParam("after", after)
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }
}
