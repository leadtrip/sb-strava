package wood.mike.sbstravaapi.controllers.global;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import wood.mike.sbstravaapi.dtos.footerlink.FooterLinkDto;
import wood.mike.sbstravaapi.services.footerlink.FooterLinkService;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final FooterLinkService footerLinkService;

    public GlobalModelAttributes(FooterLinkService footerLinkService) {
        this.footerLinkService = footerLinkService;
    }

    @ModelAttribute("allActive")
    public List<FooterLinkDto> allActive() {
        return footerLinkService.allActive();
    }
}

