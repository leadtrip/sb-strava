package wood.mike.sbstravaapi.dtos.athlete;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class AthleteTokenDto {
    @JsonProperty("athlete")
    private AthleteDto athlete;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_at")
    private Long expiresAt;
    @JsonProperty("expires_in")
    private Long expiresIn;

    public Long getStravaAthleteId() {
        return athlete.getStravaAthleteId();
    }
}
