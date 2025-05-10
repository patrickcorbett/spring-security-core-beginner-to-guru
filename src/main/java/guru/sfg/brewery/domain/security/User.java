package guru.sfg.brewery.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Custom class that mirrors the users functionality of Spring Security
 * {@link org.springframework.security.core.userdetails.UserDetails}
 * Here is the spring implementation
 * {@link org.springframework.security.core.userdetails.User}
 *
 * Use Project Lombok which is an Annotation processor to add functionality to this class at compile time
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String password;

    // The Lombok Builder would require a Set to be added via the builder method for this field
    // using the Singular annotation the builder exposes a singular method "authority" that can be called multiple times and generates a collection when the builder is built
    // the name of the singular method is determined by Lombok but in some case may need to be defined like this @Singular("authority")
    @Singular
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private Set<Authority> authorities;

    // By default the Lombok Builder nulls all fields, this annotation here allows the true value to be used as the default inside the builder
    @Builder.Default
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;

}
