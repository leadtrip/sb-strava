package wood.mike.sbstravaapi.services.footerlink;

import org.springframework.stereotype.Service;
import wood.mike.sbstravaapi.dtos.footerlink.FooterLinkDto;
import wood.mike.sbstravaapi.entities.footerlink.FooterLink;
import wood.mike.sbstravaapi.mappers.footerlink.FooterLinkMapper;
import wood.mike.sbstravaapi.repositories.footerlink.FooterLinkRepository;

import java.util.List;

@Service
public class FooterLinkService {

    private final FooterLinkRepository footerLinkRepository;
    private final FooterLinkMapper footerLinkMapper;

    public FooterLinkService(FooterLinkRepository footerLinkRepository, FooterLinkMapper footerLinkMapper) {
        this.footerLinkRepository = footerLinkRepository;
        this.footerLinkMapper = footerLinkMapper;
    }

    public List<FooterLinkDto> allActive() {
        List<FooterLink> allByIsActiveIsTrue = footerLinkRepository.findAllByIsActiveIsTrue();
        return allByIsActiveIsTrue.stream()
                .map(footerLinkMapper::toDto)
                .toList();
    }
}
