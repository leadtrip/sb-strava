package wood.mike.sbstravaapi.dtos.athlete;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class AthleteDto {
    @JsonProperty("id")
    private Long stravaAthleteId;
}
