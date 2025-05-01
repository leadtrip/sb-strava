package wood.mike.sbstravaapi.services.strava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.clients.StravaApiClient;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.dtos.athlete.AthleteDto;
import wood.mike.sbstravaapi.repositories.athlete.AthleteTokenRepository;

import java.util.List;

@Service
public class StravaService {

    private final StravaApiClient stravaApiClient;

    @Autowired
    public StravaService(StravaApiClient stravaApiClient, AthleteTokenRepository athleteTokenRepository) {
        this.stravaApiClient = stravaApiClient;
    }

    public AthleteDto getAthleteData() {
        ResponseEntity<AthleteDto> response = stravaApiClient.fetchAthlete();
        return response.getBody();
    }

    public ActivityDto getActivityData(String activityId) {
        ResponseEntity<ActivityDto> response = stravaApiClient.fetchActivity(activityId);
        return response.getBody();
    }

    public List<ActivityDto> getActivities(Integer page, Integer perPage) {
        ResponseEntity<List<ActivityDto>> response = stravaApiClient.fetchActivities(page, perPage);
        return response.getBody();
    }

    public List<ActivityDto> getActivitiesAfter(Long after) {
        ResponseEntity<List<ActivityDto>> response = stravaApiClient.activitiesAfter(after);
        return response.getBody();
    }
}
