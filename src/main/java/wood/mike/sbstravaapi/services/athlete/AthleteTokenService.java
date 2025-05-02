package wood.mike.sbstravaapi.services.athlete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.clients.StravaApiClient;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.entities.athlete.AthleteToken;
import wood.mike.sbstravaapi.repositories.athlete.AthleteRepository;
import wood.mike.sbstravaapi.repositories.athlete.AthleteTokenRepository;
import wood.mike.sbstravaapi.transformers.athlete.AthleteTokenTransformer;
import wood.mike.sbstravaapi.transformers.athlete.AthleteTransformer;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class AthleteTokenService {

    private final AthleteTokenTransformer athleteTokenTransformer;
    private final AthleteTokenRepository athleteTokenRepository;
    private final AthleteTransformer athleteTransformer;
    private final AthleteRepository athleteRepository;
    private final StravaApiClient stravaApiClient;

    public AthleteTokenService(AthleteTokenTransformer athleteTokenTransformer,
                               AthleteTokenRepository athleteTokenRepository,
                               AthleteTransformer athleteTransformer,
                               AthleteRepository athleteRepository,
                               @Lazy StravaApiClient stravaApiClient) {
        this.athleteTokenTransformer = athleteTokenTransformer;
        this.athleteTokenRepository = athleteTokenRepository;
        this.athleteTransformer = athleteTransformer;
        this.athleteRepository = athleteRepository;
        this.stravaApiClient = stravaApiClient;
    }


    public Athlete createOrUpdate(final AthleteTokenDto athleteTokenDto) {
        log.info("Looking for athlete token with athlete id {}", athleteTokenDto.getStravaAthleteId());
        Optional<AthleteToken> athleteTokenOpt = athleteTokenRepository.findByAthlete_StravaAthleteId(athleteTokenDto.getStravaAthleteId());
        if (athleteTokenOpt.isPresent()) {
            AthleteToken athleteToken = athleteTokenOpt.get();
            log.info("Updating token details for athlete: {}", athleteToken.getAthlete().getStravaAthleteId());
            athleteToken.setAccessToken(athleteTokenDto.getAccessToken());
            athleteToken.setExpiresAt(athleteTokenDto.getExpiresAt());
            athleteToken.setExpiresIn(athleteTokenDto.getExpiresIn());
            athleteToken.setRefreshToken(athleteTokenDto.getRefreshToken());
            athleteTokenRepository.save(athleteToken);
            return athleteToken.getAthlete();
        }
        else {
            log.info("Creating new athlete with Strava ID: {}", athleteTokenDto.getStravaAthleteId());
            athleteRepository.save(athleteTransformer.toEntity(athleteTokenDto.getAthlete()));
            return athleteTokenRepository.save(athleteTokenTransformer.toEntity(athleteTokenDto)).getAthlete();
        }
    }

    public AthleteToken update(AthleteToken athleteToken, AthleteTokenDto athleteTokenDto) {
        return athleteTokenRepository.save(athleteTokenTransformer.updateTokenFields(athleteToken, athleteTokenDto));
    }

    public String getValidAccessToken(Long athleteId) {
        AthleteToken token = athleteTokenRepository.findByAthleteId(athleteId)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        if (Instant.ofEpochSecond(token.getExpiresAt()).isBefore(Instant.now())) {
            log.info("Refreshing expired token for athlete ID {}", athleteId);
            AthleteTokenDto tokenDto = stravaApiClient.refreshAthleteToken(token.getRefreshToken()).getBody();
            token = update(token, tokenDto);
        }

        return token.getAccessToken();
    }
}
