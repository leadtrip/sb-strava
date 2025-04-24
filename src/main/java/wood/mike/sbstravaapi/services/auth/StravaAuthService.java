package wood.mike.sbstravaapi.services.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Service
public class StravaAuthService {

    private final String stravaAuthUrl;

    public StravaAuthService(
            @Value("${strava.client.id}") String clientId,
            @Value("${strava.redirect.uri}") String redirectUri) {
        this.stravaAuthUrl = UriComponentsBuilder.fromUriString("https://www.strava.com/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "read,activity:read_all")
                .build()
                .toUriString();
    }

}
