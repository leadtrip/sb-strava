package wood.mike.sbstravaapi.services.athlete;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.athlete.AthleteRepository;

import java.util.Optional;

@Service
public class AthleteService {
    private final AthleteRepository athleteRepository;
    private final HttpSession httpSession;


    public AthleteService(AthleteRepository athleteRepository, HttpSession httpSession) {
        this.athleteRepository = athleteRepository;
        this.httpSession = httpSession;
    }

    public Optional<Athlete> getCurrentlyLoggedInAthlete() {
        return getAthlete((Long) httpSession.getAttribute(Constants.ATHLETE_ID));
    }

    public Optional<Athlete> getAthlete(Long id) {
        return athleteRepository.findById(id);
    }

    public Optional<Athlete> getAthleteByStravaId(Long stravaId) {return athleteRepository.findByStravaAthleteId(stravaId);}
}
