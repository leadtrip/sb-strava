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
            @Value("${strava.redirect.uri}") String redirectUri,
            @Value("${strava.oauth.url}") String url,
            @Value("${strava.oauth.scope}") String scope) {
        this.stravaAuthUrl = UriComponentsBuilder.fromUriString(url)
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .build()
                .toUriString();
    }

}
