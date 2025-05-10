package guru.sfg.brewery.domain.security;

import javax.persistence.*;
import java.util.Set;

/**
 * Custom class that mirrors the users functionality of Spring Security
 * {@link org.springframework.security.core.userdetails.UserDetails}
 * Here is the spring implementation
 * {@link org.springframework.security.core.userdetails.User}
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
    private Set<Authority> authorities;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;

}
