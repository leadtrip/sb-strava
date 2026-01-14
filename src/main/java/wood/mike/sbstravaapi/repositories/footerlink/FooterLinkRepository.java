package wood.mike.sbstravaapi.repositories.footerlink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wood.mike.sbstravaapi.entities.footerlink.FooterLink;

import java.util.List;

@Repository
public interface FooterLinkRepository extends JpaRepository<FooterLink, Long> {
    List<FooterLink> findAllByIsActiveIsTrue();
}
