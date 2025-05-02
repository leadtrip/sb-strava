package wood.mike.sbstravaapi.controllers.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.view.RedirectView;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.athlete.AthleteTokenService;
import wood.mike.sbstravaapi.services.strava.StravaSyncService;

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
    private final StravaSyncService stravaSyncService;

    public StravaCallbackController(RestClient.Builder builder, AthleteTokenService athleteTokenService, StravaSyncService stravaSyncService) {
        this.restClient = builder.baseUrl("https://www.strava.com/api/v3").build();
        this.athleteTokenService = athleteTokenService;
        this.stravaSyncService = stravaSyncService;
    }

    @GetMapping("/oauth/callback")
    public RedirectView handleStravaCallback(@RequestParam("code") String authorizationCode, HttpServletRequest request) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", authorizationCode);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", redirectUri);

        ResponseEntity<AthleteTokenDto> responseEntity = this.restClient.post()
                .uri("/oauth/token")
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                .body(body)
                .retrieve()
                .toEntity(AthleteTokenDto.class);

        AthleteTokenDto athleteTokenDto = responseEntity.getBody();

        if (responseEntity.getStatusCode().is2xxSuccessful() && athleteTokenDto != null) {
            log.info("Successful login for Strava athlete: {}", athleteTokenDto);
            Athlete athlete = athleteTokenService.getAthlete(athleteTokenDto);
            request.getSession().setAttribute(Constants.ATHLETE_ID, athlete.getId());
            stravaSyncService.syncAthlete(athlete.getId(), 1);
            return new RedirectView("/profile");
        } else {
            log.error("Error retrieving athlete data: {}", responseEntity.getStatusCode());
            return new RedirectView("/login?error=login_failed");
        }
    }
}