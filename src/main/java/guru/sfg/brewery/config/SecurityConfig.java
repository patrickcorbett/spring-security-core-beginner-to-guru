package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestUrlAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    protected RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager) {
        RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Tell Spring security to use the custom Filter
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // Disable Cross Site Request Forgery Filter
                .csrf().disable();

        // Tell Spring security to use the custom Filter
        http.addFilterBefore(restUrlAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests(authorize -> {
            // allow specific requests using path matching, these requests should be allowed without authentication
            authorize
                    // enable the H2 Console to be reached without authentication
                    .antMatchers("/h2-console/**").permitAll() // Do not use in production
                    .antMatchers("/", "/webjars/**", "/login", "/resources/**", "/beers", "/beers/find").permitAll()
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

        // H2 console config
        // the H2 Console uses Frames in the frontend frame options need to be provided
        // Same origin allows frames as long as they load data from teh same origin (domain) as the main page.
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        // The default DelegatingPasswordEncoder can help when multiple encoders are being used at the same time.
        // this can be useful for migration purposes
        // Everything would still work even without this bean as it is the default
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // the AuthenticationManagerBuilder can also be used with it's Fluent API
        // This allows for the definition and configuration of the InMemoryUserDetailsManager as before
        auth.inMemoryAuthentication() // returns InMemoryUserDetailsManagerConfigurer
                .withUser("spring")
                .password("{bcrypt}$2a$10$kT8.UGKevbEOgFoL33tXbOytLcmqYqyaexqWG.VE4nVByXEJWmIFC") // Specify the encoder and the password as the hash result of the password, our provided password will be matched to this hash
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{sha256}f9b3d87c9c8fda86ef855248011babd47d01f96eb4ea5a8f371bf311c9dc941f5f5200c9b5e28ffa") // Specify the encoder and the password as the hash result of the password, our provided password will be matched to this hash
                .roles("USER");

        // The Fluent API can also be called multiple times and on a new line like this
        auth.inMemoryAuthentication()
                .withUser("scott")
                .password("{bcrypt10}$2a$10$nyJZEqdVh1fcDMADKTluN.LCHWlz07gOVQEoNcTNb.q3Rols6.ApC") // Specify the password as the hash result of the password, our provided password will be matched to this hash
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
