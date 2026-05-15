package wood.mike.sbstravaapi.controllers.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.services.activity.StatisticsService;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

import java.time.LocalDate;
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
            @PathVariable String reportType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        Athlete athlete = athleteService.getCurrentlyLoggedInAthleteOrThrow();
        return ResponseEntity.ok(statisticsService.getWeeklyStatistics(reportType, athlete, fromDate, toDate));
    }
}
