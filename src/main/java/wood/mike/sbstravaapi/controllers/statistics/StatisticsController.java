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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Map;

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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) String activityType) {
        log.info("Fetching {} statistics from: {} to: {} for activity type: {}", reportType, fromDate, toDate, activityType);
        Athlete athlete = athleteService.getCurrentlyLoggedInAthleteOrThrow();
        return ResponseEntity.ok(statisticsService.getWeeklyStatistics(reportType, athlete, fromDate, toDate, activityType));
    }

    @GetMapping("/statistics/util/shift-iso")
    @ResponseBody
    public ResponseEntity<Map<String, String>> shiftIsoWeeks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam int yearsAgo) {

        int fromWeek = from.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int toWeek = to.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        int targetYearFrom = from.get(IsoFields.WEEK_BASED_YEAR) - yearsAgo;
        int targetYearTo = to.get(IsoFields.WEEK_BASED_YEAR) - yearsAgo;

        LocalDate shiftedFrom = LocalDate.now()
                .with(IsoFields.WEEK_BASED_YEAR, targetYearFrom)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, fromWeek)
                .with(DayOfWeek.MONDAY);

        LocalDate shiftedTo = LocalDate.now()
                .with(IsoFields.WEEK_BASED_YEAR, targetYearTo)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, toWeek)
                .with(DayOfWeek.MONDAY);

        return ResponseEntity.ok(Map.of(
                "from", shiftedFrom.toString(),
                "to", shiftedTo.toString()
        ));
    }
}
