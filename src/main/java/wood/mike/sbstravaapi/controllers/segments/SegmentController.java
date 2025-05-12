package wood.mike.sbstravaapi.controllers.segments;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wood.mike.sbstravaapi.services.segments.SegmentService;

@Controller("segments")
public class SegmentController {

    private SegmentService segmentService;

    public SegmentController(final SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @GetMapping("/sync")
    public String syncStarredSegments(Model model) {
        model.addAttribute("pageTitle", "Sync starred segments");
        model.addAttribute("templateName", "segments/sync");
        return "layout";
    }
}
