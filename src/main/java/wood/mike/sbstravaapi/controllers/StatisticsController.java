package wood.mike.sbstravaapi.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wood.mike.sbstravaapi.config.Constants;
import wood.mike.sbstravaapi.entities.athlete.Athlete;
import wood.mike.sbstravaapi.repositories.activity.WeeklySufferScore;
import wood.mike.sbstravaapi.services.activity.StatisticsService;
import wood.mike.sbstravaapi.services.athlete.AthleteService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/statistics/report")
    public String loadReportFragment(@RequestParam("reportType") String reportType,
                                     Model model,
                                     HttpSession session) {
        Long athleteId = (Long) session.getAttribute(Constants.ATHLETE_ID);
        Optional<Athlete> athlete = athleteService.getAthlete(athleteId);
        if (athlete.isEmpty()) {
            model.addAttribute("errorMessage", "Athlete not found");
            return "statistics/statistics :: errorMessage";
        }

        switch (reportType) {
            case "weekly":
                List<WeeklySufferScore> weeklyScores = statisticsService.getWeeklySufferScores(athlete.get());
                model.addAttribute("weeklyScores", weeklyScores);   // adding this for tabular format, can be removed

                List<String> labels = weeklyScores.stream()
                        .map(score -> score.getWeekStartDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                        .collect(Collectors.toList());

                List<Long> values = weeklyScores.stream()
                        .map(WeeklySufferScore::getTotalScore)
                        .collect(Collectors.toList());

                model.addAttribute("labels", labels);
                model.addAttribute("values", values);
                return "statistics/statistics :: weekly";
            default:
                model.addAttribute("errorMessage", "Unknown report type");
                return "statistics/statistics :: errorMessage";
        }
    }
}
