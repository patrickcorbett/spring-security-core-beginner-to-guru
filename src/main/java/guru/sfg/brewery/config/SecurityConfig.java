package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> {
            // allow specific requests using path matching, these requests should be allowed without authentication
            authorize.antMatchers("/", "/webjars/**", "/login", "/resources/**", "/beers", "/beers/find").permitAll()
                    /*
                     * "/api/v1/user/*" - will match any value, up to another "/"
                     * "/api/v1/user/**" - will match all values beginning with start of string (including if another "/" is found.
                     */
                    .antMatchers("/beers*", "/beers/find").permitAll()
                    // Limit a Path by HTTP Method and Path
                    .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                    // MVC Matchers allow the usage of path variables rather than using antMatcher and wildcards
                    .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
        }).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
    }

    /*
     * Providing a BEan with the name userDetailsService overrides the default Spring boot Auto Config.
     * Here a new UserDetailsService with hard coded usernames and passwords is defined.
     * This creates essentially a HashMap of users for use in memory
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("spring")
                .password("guru")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }















}
