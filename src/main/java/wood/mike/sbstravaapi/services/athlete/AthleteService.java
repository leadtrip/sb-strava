package wood.mike.sbstravaapi.services.athlete;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.athlete.AthleteRepository;

import java.util.Optional;

@Service
public class AthleteService {
    private final AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public Optional<Athlete> getAthlete(Long id) {
        return athleteRepository.findById(id);
    }

    public Optional<Athlete> getAthleteByStravaId(Long stravaId) {return athleteRepository.findByStravaAthleteId(stravaId);}
}
