package wood.mike.sbstravaapi.controllers.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import wood.mike.sbstravaapi.clients.StravaApiClient;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.athlete.AthleteTokenService;
import wood.mike.sbstravaapi.services.strava.StravaSyncService;

import static wood.mike.sbstravaapi.config.Constants.REDIRECT_AFTER_LOGIN;

@Slf4j
@Controller
public class StravaCallbackController {

    private final AthleteTokenService athleteTokenService;
    private final StravaSyncService stravaSyncService;
    private final StravaApiClient stravaApiClient;

    public StravaCallbackController(AthleteTokenService athleteTokenService,
                                    StravaSyncService stravaSyncService,
                                    StravaApiClient stravaApiClient) {
        this.stravaApiClient = stravaApiClient;
        this.athleteTokenService = athleteTokenService;
        this.stravaSyncService = stravaSyncService;
    }

    @GetMapping("/oauth/callback")
    public RedirectView handleStravaCallback(@RequestParam("code") String authorizationCode, HttpServletRequest request) {
        ResponseEntity<AthleteTokenDto> responseEntity = this.stravaApiClient.fetchAthleteToken(authorizationCode);
        AthleteTokenDto athleteTokenDto = responseEntity.getBody();

        if (responseEntity.getStatusCode().is2xxSuccessful() && athleteTokenDto != null) {
            log.info("Successful login for Strava athlete: {}", athleteTokenDto.getStravaAthleteId());
            Athlete athlete = athleteTokenService.createOrUpdate(athleteTokenDto);
            request.getSession().setAttribute(Constants.ATHLETE_ID, athlete.getId());
            stravaSyncService.syncAthlete(athlete.getId(), 1);

            String redirect = (String) request.getSession()
                    .getAttribute(REDIRECT_AFTER_LOGIN);
            if (redirect != null) {
                request.getSession().removeAttribute(REDIRECT_AFTER_LOGIN);
                return new RedirectView(redirect);
            }
            return new RedirectView("/profile");
        } else {
            log.error("Error retrieving athlete data: {}", responseEntity.getStatusCode());
            return new RedirectView("/login?error=login_failed");
        }
    }
}