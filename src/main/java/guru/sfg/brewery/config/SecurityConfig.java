package guru.sfg.brewery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
                    .antMatchers("/beers*", "/beers/find").permitAll();
        }).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
    }
}
