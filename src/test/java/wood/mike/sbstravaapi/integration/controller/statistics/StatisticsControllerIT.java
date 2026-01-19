package wood.mike.sbstravaapi.integration.controller.statistics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.integration.BaseIntegrationTest;
import wood.mike.sbstravaapi.repositories.athlete.AthleteRepository;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StatisticsControllerIT extends BaseIntegrationTest {

    @Autowired
    AthleteRepository athleteRepository;

    @Test
    void returnsWeeklyStatisticsWhenLoggedIn() throws Exception {
        Athlete athlete = createAndSaveAthlete();

        mockMvc.perform(get("/statistics/data/load")
                        .session(loggedInSession(athlete.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void redirectsToLoginWhenNoAthleteInSession() throws Exception {
        mockMvc.perform(get("/statistics/data/load"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }


    @Test
    void returns400ForUnknownReportType() throws Exception {
        Athlete athlete = createAndSaveAthlete();

        mockMvc.perform(get("/statistics/data/foo")
                        .session(loggedInSession(athlete.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unknown report type"));
    }

    private Athlete createAndSaveAthlete() {
        Athlete athlete = new Athlete();
        athlete.setFirstname("Bob");
        athlete.setLastname("Builder");
        athlete.setStravaAthleteId(9291819L);
        athlete.setCreatedAt(LocalDateTime.now());
        return athleteRepository.save(athlete);
    }
}
