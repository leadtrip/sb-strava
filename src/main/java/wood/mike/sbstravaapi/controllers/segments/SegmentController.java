package wood.mike.sbstravaapi.controllers.segments;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wood.mike.sbstravaapi.dtos.segments.SummarySegmentRow;
import wood.mike.sbstravaapi.entities.segments.SummarySegment;
import wood.mike.sbstravaapi.services.segments.SummarySegmentService;
import wood.mike.sbstravaapi.utils.ActivityFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SegmentController {

    private final SummarySegmentService summarySegmentService;
    private final ActivityFormatter activityFormatter;

    public SegmentController(SummarySegmentService summarySegmentService,
                             ActivityFormatter activityFormatter) {
        this.summarySegmentService = summarySegmentService;
        this.activityFormatter = activityFormatter;
    }

    @GetMapping("/segments/list")
    public String getLocalActivities(Model model) {
        model.addAttribute("pageTitle", "Starred segments");
        model.addAttribute("templateName", "segments/list");
        return "layout";
    }

    @GetMapping("/segments/filtered")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam int draw,
            @RequestParam int start,
            @RequestParam int length,
            @RequestParam(required = false) String activityType
    ) {
        int page = start / length;

        Page<SummarySegment> starredSegments = summarySegmentService.getStarredSegments(page, length, activityType);
        List<SummarySegmentRow> rows = starredSegments.getContent().stream()
                .map(segment -> new SummarySegmentRow(segment, activityFormatter))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("draw", draw);
        response.put("recordsTotal", summarySegmentService.countAll());
        response.put("recordsFiltered", starredSegments.getTotalElements());
        response.put("data", rows);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/segments/sync")
    public String sync(Model model) {
        model.addAttribute("pageTitle", "Sync starred segments");
        model.addAttribute("templateName", "segments/sync");
        return "layout";
    }

    @PostMapping("/segments/syncStarredSegments")
    public ResponseEntity<Integer> syncStarredSegments() {
        Integer totalSynced = summarySegmentService.syncStarredSegments(1, 100);
        return ResponseEntity.ok(totalSynced);
    }
}
