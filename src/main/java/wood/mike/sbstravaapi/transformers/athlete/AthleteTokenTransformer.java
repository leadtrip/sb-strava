package wood.mike.sbstravaapi.transformers.athlete;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.athlete.AthleteTokenDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.entities.athlete.AthleteToken;

@Service
public class AthleteTokenTransformer {
    public AthleteToken toEntity(AthleteTokenDto athleteTokenDto, Athlete athlete) {
        AthleteToken athleteToken = new AthleteToken();
        athleteToken.setAccessToken(athleteTokenDto.getAccessToken());
        athleteToken.setExpiresAt(athleteTokenDto.getExpiresAt());
        athleteToken.setExpiresIn(athleteTokenDto.getExpiresIn());
        athleteToken.setRefreshToken(athleteTokenDto.getRefreshToken());
        athleteToken.setAthlete(athlete);
        return athleteToken;
    }

    public AthleteTokenDto toDto(AthleteToken athleteToken) {
        AthleteTokenDto athleteTokenDto = new AthleteTokenDto();
        athleteTokenDto.setAccessToken(athleteToken.getAccessToken());
        athleteTokenDto.setExpiresAt(athleteToken.getExpiresAt());
        athleteTokenDto.setExpiresIn(athleteToken.getExpiresIn());
        athleteTokenDto.setRefreshToken(athleteToken.getRefreshToken());
        return athleteTokenDto;
    }
}
