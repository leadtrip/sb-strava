package wood.mike.sbstravaapi.transformers.athlete;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.entities.athlete.AthleteToken;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

@Service
public class AthleteTokenTransformer {

    private final AthleteService athleteService;

    public AthleteTokenTransformer(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    public AthleteToken toEntity(AthleteTokenDto athleteTokenDto) {
        AthleteToken athleteToken = new AthleteToken();
        athleteToken.setAccessToken(athleteTokenDto.getAccessToken());
        athleteToken.setExpiresAt(athleteTokenDto.getExpiresAt());
        athleteToken.setExpiresIn(athleteTokenDto.getExpiresIn());
        athleteToken.setRefreshToken(athleteTokenDto.getRefreshToken());
        athleteService.getAthleteByStravaId(athleteTokenDto.getStravaAthleteId()).ifPresent(athleteToken::setAthlete);
        return athleteToken;
    }

    public AthleteToken updateTokenFields(AthleteToken token, AthleteTokenDto dto) {
        token.setAccessToken(dto.getAccessToken());
        token.setRefreshToken(dto.getRefreshToken());
        token.setExpiresAt(dto.getExpiresAt());
        token.setExpiresIn(dto.getExpiresIn());
        return token;
    }
}
