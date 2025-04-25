package wood.mike.sbstravaapi.services.strava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import wood.mike.sbstravaapi.clients.StravaApiClient;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;
import wood.mike.sbstravaapi.entities.athlete.AthleteToken;
import wood.mike.sbstravaapi.repositories.athlete.AthleteTokenRepository;

import java.util.List;

@Service
public class StravaService {

    private final StravaApiClient stravaApiClient;
    private final AthleteTokenRepository athleteTokenRepository;

    @Autowired
    public StravaService(StravaApiClient stravaApiClient, AthleteTokenRepository athleteTokenRepository) {
        this.stravaApiClient = stravaApiClient;
        this.athleteTokenRepository = athleteTokenRepository;
    }

    AthleteToken findByAthleteId(Long athleteId) {
        return athleteTokenRepository.findByAthleteId(athleteId).orElseThrow(() -> new RuntimeException("Athlete not found"));
    }

    public AthleteDto getAthleteData(Long athleteId) {
        ResponseEntity<AthleteDto> response = stravaApiClient.fetchAthlete(findByAthleteId(athleteId).getAccessToken());
        return response.getBody();
    }

    public ActivityDto getActivityData(String activityId, Long athleteId) {
        ResponseEntity<ActivityDto> response = stravaApiClient.fetchActivity(activityId, findByAthleteId(athleteId).getAccessToken());
        return response.getBody();
    }

    public List<ActivityDto> getActivities(Integer page, Integer perPage, Long athleteId) {
        ResponseEntity<List<ActivityDto>> response = stravaApiClient.fetchActivities(page, perPage, findByAthleteId(athleteId).getAccessToken());
        return response.getBody();
    }

    public List<ActivityDto> getActivitiesAfter(Long after, Long athleteId) {
        ResponseEntity<List<ActivityDto>> response = stravaApiClient.activitiesAfter(after, findByAthleteId(athleteId).getAccessToken());
        return response.getBody();
    }
}
