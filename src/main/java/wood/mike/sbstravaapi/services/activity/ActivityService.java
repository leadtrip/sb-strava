package wood.mike.sbstravaapi.services.activity;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Optional<Activity> getActivity(Long activityId) {
        return activityRepository.findById(activityId);
    }

    public List<Activity> getLatestActivities(Athlete athlete, int numberOfActivities) {
        Pageable pageable = PageRequest.of(0, numberOfActivities, Sort.by("startDate").descending());
        return activityRepository.findByAthleteOrderByStartDateDesc(athlete, pageable);
    }
}
