package guru.sfg.brewery.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Custom class that mirrors the authorities functionality of Spring Security
 * {@link org.springframework.security.core.GrantedAuthority}
 * Here is the spring implementation
 * {@link org.springframework.security.core.authority.SimpleGrantedAuthority}
 *
 * Use Project Lombok which is an Annotation processor to add functionality to this class at compile time
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
