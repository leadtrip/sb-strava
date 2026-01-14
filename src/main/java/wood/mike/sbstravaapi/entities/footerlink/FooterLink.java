package wood.mike.sbstravaapi.entities.footerlink;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "footer_link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FooterLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "text")
    private String text;

    @Column(name = "is_active")
    private Boolean isActive;
}
