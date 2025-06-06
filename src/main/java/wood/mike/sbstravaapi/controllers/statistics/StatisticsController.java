package wood.mike.sbstravaapi.controllers.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.activity.StatisticsService;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final AthleteService athleteService;

    public StatisticsController(StatisticsService statisticsService, AthleteService athleteService) {
        this.statisticsService = statisticsService;
        this.athleteService = athleteService;
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("pageTitle", "Statistics");
        model.addAttribute("templateName", "statistics/index");
        return "layout";
    }

    @GetMapping("/statistics/data/{reportType}")
    @ResponseBody
    public ResponseEntity<?> getReportData(
            @PathVariable("reportType") String reportType) {
        Optional<Athlete> athlete = athleteService.getCurrentlyLoggedInAthlete();
        return athlete.<ResponseEntity<?>>map(value -> switch (reportType) {
            case "load", "distance" -> ResponseEntity.ok(statisticsService.getWeeklyStatistics(reportType, value));
            default -> ResponseEntity.badRequest().body(Map.of("error", "Unknown report type"));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Athlete not found")));

    }
}
