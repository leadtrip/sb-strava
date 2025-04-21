package wood.mike.sbstravaapi.transformers.athlete;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

@Service
public class AthleteTransformer {
    public Athlete toEntity(AthleteDto athleteDto) {
        Athlete athlete = new Athlete();
        athlete.setStravaAthleteId(athleteDto.getStravaAthleteId());
        return athlete;
    }
}
