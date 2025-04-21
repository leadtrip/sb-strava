package wood.mike.sbstravaapi.controllers.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.athlete.AthleteTokenService;

@Slf4j
@Controller
public class StravaCallbackController {

    @Value("${strava.client.id}")
    private String clientId;

    @Value("${strava.client.secret}")
    private String clientSecret;

    @Value("${strava.redirect.uri}")
    private String redirectUri;

    private static final String STRAVA_TOKEN_URL = "https://www.strava.com/oauth/token";

    private final RestClient restClient;
    private final AthleteTokenService athleteTokenService;

    public StravaCallbackController(RestClient.Builder builder, AthleteTokenService athleteTokenService) {
        this.restClient = builder.baseUrl("https://www.strava.com/api/v3").build();
        this.athleteTokenService = athleteTokenService;
    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<? extends Object> handleStravaCallback(@RequestParam("code") String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", authorizationCode);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<AthleteTokenDto> responseEntity = this.restClient.post()
                .uri("/oauth/token")
                .body(body)
                .retrieve()
                .toEntity(AthleteTokenDto.class);

        AthleteTokenDto athleteTokenDto = responseEntity.getBody();

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Successful login for Strava athlete: {}", athleteTokenDto);
            Athlete athlete = athleteTokenService.getAthlete(athleteTokenDto);
            return ResponseEntity.ok(athlete);
        } else {
            System.err.println("Error retrieving athlete data: " + responseEntity.getStatusCode());
            return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
        }
    }
}