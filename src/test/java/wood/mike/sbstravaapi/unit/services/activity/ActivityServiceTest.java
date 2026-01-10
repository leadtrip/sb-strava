package wood.mike.sbstravaapi.unit.services.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wood.mike.sbstravaapi.dtos.activity.ActivityDto;
import wood.mike.sbstravaapi.entities.activity.Activity;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.mappers.activity.ActivityMapper;
import wood.mike.sbstravaapi.repositories.activity.ActivityRepository;
import wood.mike.sbstravaapi.repositories.activity.ActivitySource;
import wood.mike.sbstravaapi.services.activity.ActivityService;
import wood.mike.sbstravaapi.services.activity.ActivityStreamDataService;
import wood.mike.sbstravaapi.services.athlete.AthleteService;
import wood.mike.sbstravaapi.services.strava.StravaService;
import wood.mike.sbstravaapi.transformers.activity.ActivityTransformer;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static wood.mike.sbstravaapi.repositories.activity.ActivitySource.SEGMENT_PR;
import static wood.mike.sbstravaapi.repositories.activity.ActivitySource.SYNC;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ActivityTransformer activityTransformer;
    @Mock
    private StravaService stravaService;
    @Mock
    private AthleteService athleteService;
    @Mock
    private ActivityMapper activityMapper;
    @Mock
    private ActivityStreamDataService activityStreamDataService;

    @Test
    public void testGetActivity_activity_exists_locally() {
        Long activityId = 1L;
        Long stravaActivityId = 30210L;

        Activity localDbActivity = new Activity();
        localDbActivity.setId(activityId);
        localDbActivity.setStravaActivityId(stravaActivityId);

        when(activityRepository.findByStravaActivityId(stravaActivityId)).thenReturn(Optional.of(localDbActivity));

        Activity activity = activityService.getActivity(stravaActivityId, SEGMENT_PR);

        verify(activityTransformer, never()).toEntity(any());
        verify(activityRepository, never()).save(any());
        verify(activityStreamDataService, never()).existsByActivityId(activityId);

        assertThat(activity.getStravaActivityId()).isEqualTo(stravaActivityId);
    }

    @Test
    public void testGetActivity_activity_does_not_exist_locally(){
        Long activityId = 1L;
        Long stravaActivityId = 30210L;

        Activity localDbActivity = new Activity();
        localDbActivity.setId(activityId);
        localDbActivity.setStravaActivityId(stravaActivityId);

        Optional<Activity> missingLocalActivity = Optional.empty();

        when(activityRepository.findByStravaActivityId(stravaActivityId)).thenReturn(missingLocalActivity);
        when(activityTransformer.toEntity(any())).thenReturn(localDbActivity);
        when(activityRepository.save(any())).thenReturn(localDbActivity);
        when(activityStreamDataService.existsByActivityId(activityId)).thenReturn(true);
        verify(activityStreamDataService, never()).fetchAndStoreActivityStreams(any(), any());

        Activity activity = activityService.getActivity(stravaActivityId, SYNC);
        assertThat(activity.getStravaActivityId()).isEqualTo(stravaActivityId);
    }

    @Test
    public void testSyncActivities() {
        Athlete bob = new Athlete();
        LocalDateTime activityStartDate = LocalDateTime.of(2025, Month.APRIL, 1, 10, 0);
        Activity oldestSyncedActivity = new Activity();
        oldestSyncedActivity.setSource(SYNC);
        oldestSyncedActivity.setStartDate(activityStartDate);

        ActivityDto stravaActivity1 = new ActivityDto();
        stravaActivity1.setId(1L);
        ActivityDto stravaActivity2 = new ActivityDto();
        stravaActivity2.setId(2L);
        List<ActivityDto> stravaActivities = List.of(stravaActivity1, stravaActivity2);

        when(athleteService.getCurrentlyLoggedInAthleteOrThrow()).thenReturn(bob);
        when(activityRepository.findFirstByAthleteAndSourceOrderByStartDateAsc(bob, SYNC)).thenReturn(Optional.of(oldestSyncedActivity));
        when(stravaService.getActivitiesBefore(activityStartDate.toEpochSecond(ZoneOffset.UTC))).thenReturn(stravaActivities);
        when(activityRepository.findByStravaActivityId(anyLong())).thenReturn(Optional.empty());
        when(activityTransformer.toEntity(any())).thenAnswer(dto -> {
            ActivityDto activityDto = dto.getArgument(0);
            Activity activity = new Activity();
            activity.setStravaActivityId(activityDto.getId());
            return activity;
        });

        assertThat(activityService.syncActivities(1)).isEqualTo(2);

        verify(activityTransformer, times(stravaActivities.size())).toEntity(any());
    }
}
