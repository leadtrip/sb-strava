package wood.mike.sbstravaapi.unit.services.athlete;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.athlete.AthleteRepository;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AthleteServiceTest {

    @InjectMocks
    private AthleteService athleteService;

    @Mock
    private AthleteRepository athleteRepository;

    @Test
    public void testGetAthlete() {
        Long athleteId = 1L;
        Athlete athlete = new Athlete();
        athlete.setId(athleteId);;
        when(athleteRepository.findById(athleteId)).thenReturn(Optional.of(athlete));
        assertThat(athleteService.getAthlete(athleteId)).isPresent();
    }
}
