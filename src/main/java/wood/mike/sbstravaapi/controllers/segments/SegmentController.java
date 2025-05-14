package wood.mike.sbstravaapi.controllers.segments;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import wood.mike.sbstravaapi.services.segments.SegmentService;

@Controller
public class SegmentController {

    private final SegmentService segmentService;

    public SegmentController(final SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @GetMapping("/segments/list")
    public String list(Model model) {
        model.addAttribute("pageTitle", "Starred segments");
        model.addAttribute("templateName", "segments/list");
        return "layout";
    }

    @GetMapping("/segments/sync")
    public String sync(Model model) {
        model.addAttribute("pageTitle", "Sync starred segments");
        model.addAttribute("templateName", "segments/sync");
        return "layout";
    }

    @PostMapping("/segments/syncStarredSegments")
    public ResponseEntity<Integer> syncStarredSegments() {
        Integer totalSynced = segmentService.syncStarredSegments(1, 100);
        return ResponseEntity.ok(totalSynced);
    }
}
