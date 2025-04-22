package wood.mike.sbstravaapi.dtos.athlete;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class AthleteDto {
    @JsonProperty("id")
    private Long stravaAthleteId;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("profile_medium")
    private String profileMedium;
    @JsonProperty("profile")
    private String profile;
    @JsonProperty("country")
    private String country;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("ftp")
    private String ftp;
    @JsonProperty("weight")
    private String weight;
}
