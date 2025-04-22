package wood.mike.sbstravaapi.transformers.athlete;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;
import wood.mike.sbstravaapi.entities.athlete.Athlete;

@Service
public class AthleteTransformer {
    public Athlete toEntity(AthleteDto athleteDto) {
        Athlete athlete = new Athlete();
        athlete.setStravaAthleteId(athleteDto.getStravaAthleteId());
        athlete.setFirstname(athleteDto.getFirstname());
        athlete.setLastname(athleteDto.getLastname());
        athlete.setCountry(athleteDto.getCountry());
        athlete.setSex(athleteDto.getSex());
        athlete.setFtp(athleteDto.getFtp());
        athlete.setProfile(athleteDto.getProfile());
        athlete.setProfileMedium(athleteDto.getProfileMedium());
        athlete.setWeight(athleteDto.getWeight());
        return athlete;
    }
}
