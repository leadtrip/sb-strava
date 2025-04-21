package wood.mike.sbstravaapi.controllers.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class StravaAuthController {

    @Value("${strava.client.id}")
    private String clientId;

    @Value("${strava.redirect.uri}")
    private String redirectUri;

    private static final String STRAVA_AUTH_URL = "https://www.strava.com/oauth/authorize";
    private static final String SCOPE = "activity:read_all";

    @GetMapping("/login/strava")
    public RedirectView redirectToStrava() {
        String authorizationUrl = String.format(
                "%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&approval_prompt=auto",
                STRAVA_AUTH_URL,
                clientId,
                redirectUri,
                SCOPE
        );
        return new RedirectView(authorizationUrl);
    }
}
