package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

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

    @Bean
    protected PasswordEncoder passwordEncoder() {
        // Providing a Password Encoder tell Spring security to use this Encoder.
        // This is used instead of the Default DelegatingPasswordEncoder which is a password encoder that delegates to
        // another PasswordEncoder based upon a prefixed identifier.
        // we used this before like this "{noop}password" when defining the password for our in memory users.
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // the AuthenticationManagerBuilder can also be used with it's Fluent API
        // This allows for the definition and configuration of the InMemoryUserDetailsManager as before
        auth.inMemoryAuthentication() // returns InMemoryUserDetailsManagerConfigurer
                .withUser("spring")
                .password("$2a$10$yqcIzJTwCKllv1iqPl.y2O9r7hEecEG3bbhq25NWBH86E/HhkUaYO") // Specify the password as the hash result of the password, our provided password will be matched to this hash
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("$2a$10$yqcIzJTwCKllv1iqPl.y2O9r7hEecEG3bbhq25NWBH86E/HhkUaYO") // Specify the password as the hash result of the password, our provided password will be matched to this hash
                .roles("USER");

        // The Fluent API can also be called multiple times and on a new line like this
        auth.inMemoryAuthentication()
                .withUser("scott")
                .password("$2a$10$hHGnyUxTxQO/piKCmjf2oO0yk1CGsa7IX8vjnoIBu71.qtb0kyHLm") // Specify the password as the hash result of the password, our provided password will be matched to this hash
                .roles("CUSTOMER");
    }

    /*
     * Providing a BEan with the name userDetailsService overrides the default Spring boot Auto Config.
     * Here a new UserDetailsService with hard coded usernames and passwords is defined.
     * This creates essentially a HashMap of users for use in memory
     */
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }


}
