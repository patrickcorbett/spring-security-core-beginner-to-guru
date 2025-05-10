package guru.sfg.brewery.domain.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Custom class that mirrors the authorities functionality of Spring Security
 * {@link org.springframework.security.core.GrantedAuthority}
 * Here is the spring implementation
 * {@link org.springframework.security.core.authority.SimpleGrantedAuthority}
 */
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
