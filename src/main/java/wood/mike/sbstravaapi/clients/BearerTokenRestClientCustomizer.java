package wood.mike.sbstravaapi.clients;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.entities.athlete.AthleteToken;
import wood.mike.sbstravaapi.repositories.athlete.AthleteTokenRepository;

import java.net.URI;

@Slf4j
@Component
public class BearerTokenRestClientCustomizer implements RestClientCustomizer {

    private final AthleteTokenRepository athleteTokenRepository;
    private final HttpSession httpSession;

    public BearerTokenRestClientCustomizer(AthleteTokenRepository athleteTokenRepository, HttpSession httpSession) {
        this.athleteTokenRepository = athleteTokenRepository;
        this.httpSession = httpSession;
    }

    @Override
    public void customize(RestClient.Builder builder) {
        builder.requestInterceptor((request, body, execution) -> {
            URI uri = request.getURI();

            if (!shouldExclude(uri.getPath())) {
                request.getHeaders().add("Authorization", "Bearer " + getToken());
            }
            return execution.execute(request, body);
        });
    }

    private boolean shouldExclude(String path) {
        return path.endsWith("/oauth/token");
    }

    String getToken() {
        Long athleteId = (Long) httpSession.getAttribute(Constants.ATHLETE_ID);
        if (athleteId == null) {
            throw new RuntimeException("Athlete ID not found in session");
        }
        AthleteToken token = athleteTokenRepository.findByAthleteId(athleteId).orElseThrow(() -> new RuntimeException("Athlete not found"));
        return token.getAccessToken();
    }
}