package com.vhs.rental.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select username, password, enabled from users where username=?");

        // define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select username, authority from authorities where username=?");

        return jdbcUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.GET, "/api/vhs").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/rentals").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE, "/api/rentals").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.POST, "/api/rentals").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/rentals/addRentalForm/**").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/allVhs").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/allUsers").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/rentals/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/vhs").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/vhs").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/vhs").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/vhs/addVhsForm").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout-> logout.permitAll())
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}