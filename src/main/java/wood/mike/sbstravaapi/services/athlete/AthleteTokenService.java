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


    public Athlete getAthlete(AthleteTokenDto athleteTokenDto) {
        Optional<AthleteToken> athleteToken = athleteTokenRepository.findByAthlete_Id(athleteTokenDto.getStravaAthleteId());
        if (athleteToken.isPresent()) {
            log.info("Found athlete: {}", athleteToken.get().getStravaAthleteId());
            // TODO update, might be the same as just saving below
            return athleteToken.get().getAthlete();
        }
        else {
            log.info("Creating new athlete with Strava ID: {}", athleteTokenDto.getStravaAthleteId());
            Athlete athlete = athleteRepository.save(athleteTransformer.toEntity(athleteTokenDto.getAthlete()));
            return athleteTokenRepository.save(athleteTokenTransformer.toEntity(athleteTokenDto, athlete)).getAthlete();
        }
    }
}
