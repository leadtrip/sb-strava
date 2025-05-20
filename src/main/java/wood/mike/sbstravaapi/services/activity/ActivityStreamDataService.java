package wood.mike.sbstravaapi.services.activity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.activity.ActivityStreamData;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.ActivityStreamDataRepository;
import wood.mike.sbstravaapi.services.strava.StravaService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ActivityStreamDataService {
    private final StravaService stravaService;
    private final ActivityRepository activityRepository;
    private final ActivityStreamDataRepository streamDataRepository;

    public ActivityStreamDataService(StravaService stravaService,
                                     ActivityRepository activityRepository,
                                     ActivityStreamDataRepository streamDataRepository) {
        this.stravaService = stravaService;
        this.activityRepository = activityRepository;
        this.streamDataRepository = streamDataRepository;
    }

    public void fetchAndStoreActivityStreams(Long activityId, String stravaId) {
        List<String> keys = List.of("heartrate", "watts", "time", "distance");
        JsonNode respBody = stravaService.getActivityStreams(stravaId, keys);

        if (respBody == null || respBody.isEmpty()) return;

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        List<ActivityStreamData> toSave = new ArrayList<>();

        for (String key : keys) {
            JsonNode stream = respBody.get(key);
            if (stream != null && stream.has("data")) {
                List<Integer> values = StreamSupport.stream(stream.get("data").spliterator(), false)
                        .map(JsonNode::asInt)
                        .toList();

                for (int i = 0; i < values.size(); i++) {
                    ActivityStreamData data = new ActivityStreamData();
                    data.setActivity(activity);
                    data.setStreamType(key);
                    data.setSequenceIndex(i);
                    data.setValue(values.get(i));
                    toSave.add(data);
                }
            }
        }

        streamDataRepository.saveAll(toSave);
    }

    public boolean existsByActivityId(Long id) {
        return streamDataRepository.existsByActivityId(id);
    }
}
