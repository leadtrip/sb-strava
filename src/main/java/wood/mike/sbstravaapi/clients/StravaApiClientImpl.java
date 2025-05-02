package wood.mike.sbstravaapi.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;

import java.util.List;

@Component
public class StravaApiClientImpl implements StravaApiClient {

    @Value("${strava.client.id}")
    private String clientId;

    @Value("${strava.client.secret}")
    private String clientSecret;


    private final RestClient restClient;

    @Autowired
    public StravaApiClientImpl(RestClient.Builder builder,
                               @Value("${strava.api.base.url}") String stravaApiBaseUrl) {
        this.restClient = builder.baseUrl(stravaApiBaseUrl).build();
    }

    @Override
    public ResponseEntity<AthleteTokenDto> fetchAthleteToken(String authorizationCode) {
        MultiValueMap<String, String> body = commonTokenBody("authorization_code");
        body.add("code", authorizationCode);
        return fetchToken(body);
    }

    @Override
    public ResponseEntity<AthleteTokenDto> refreshAthleteToken(String refreshToken) {
        MultiValueMap<String, String> body = commonTokenBody("refresh_token");
        body.add("refresh_token", refreshToken);
        return fetchToken(body);
    }

    MultiValueMap <String, String> commonTokenBody(String grantType) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", grantType);
        return body;
    }

    private ResponseEntity<AthleteTokenDto> fetchToken(MultiValueMap<String, String> body) {
        return this.restClient.post()
                .uri("/oauth/token")
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                .body(body)
                .retrieve()
                .toEntity(AthleteTokenDto.class);
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
