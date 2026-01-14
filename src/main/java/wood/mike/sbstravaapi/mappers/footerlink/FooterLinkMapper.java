package wood.mike.sbstravaapi.mappers.footerlink;

import org.mapstruct.Mapper;
import wood.mike.sbstravaapi.dtos.footerlink.FooterLinkDto;
import wood.mike.sbstravaapi.entities.footerlink.FooterLink;

@Mapper(componentModel = "spring")
public interface FooterLinkMapper {
    FooterLinkDto toDto(FooterLink footerLink);
}
