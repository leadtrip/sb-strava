package wood.mike.sbstravaapi.services.athlete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.entities.athlete.AthleteToken;
import wood.mike.sbstravaapi.repositories.athlete.AthleteRepository;
import wood.mike.sbstravaapi.repositories.athlete.AthleteTokenRepository;
import wood.mike.sbstravaapi.transformers.athlete.AthleteTokenTransformer;
import wood.mike.sbstravaapi.transformers.athlete.AthleteTransformer;

import java.util.Optional;

@Service
@Slf4j
public class AthleteTokenService {

    private final AthleteTokenTransformer athleteTokenTransformer;
    private final AthleteTokenRepository athleteTokenRepository;
    private final AthleteTransformer athleteTransformer;
    private final AthleteRepository athleteRepository;

    public AthleteTokenService(AthleteTokenTransformer athleteTokenTransformer, AthleteTokenRepository athleteTokenRepository, AthleteTransformer athleteTransformer, AthleteRepository athleteRepository) {
        this.athleteTokenTransformer = athleteTokenTransformer;
        this.athleteTokenRepository = athleteTokenRepository;
        this.athleteTransformer = athleteTransformer;
        this.athleteRepository = athleteRepository;
    }


    public Athlete getAthlete(final AthleteTokenDto athleteTokenDto) {
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
            Athlete athlete = athleteRepository.save(athleteTransformer.toEntity(athleteTokenDto.getAthlete()));
            return athleteTokenRepository.save(athleteTokenTransformer.toEntity(athleteTokenDto, athlete)).getAthlete();
        }
    }
}
