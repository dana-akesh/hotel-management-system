package com.bzu.hotel_management_system.config;

import com.bzu.hotel_management_system.controller.EmployeeController;
import lombok.RequiredArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.bzu.hotel_management_system.entity.Permission.*;
import static com.bzu.hotel_management_system.entity.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/auth/authenticate",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-ui.html"
            };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Security Filter Chain - {}", ADMIN.name());
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/users/employees/**").hasRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/users/employees/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/users/employees/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/users/employees/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/users/employees/**").hasAuthority(ADMIN_DELETE.name())
                                .requestMatchers("/api/v1/users/customers/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/users/customers/**").hasAnyAuthority(ADMIN_READ.name(), CUSTOMER_READ.name())
                                .requestMatchers(PUT, "/api/v1/users/customers/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(PATCH, "/api/v1/users/customers/**").hasAnyAuthority(ADMIN_UPDATE.name(), CUSTOMER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/users/customers/**").hasAuthority(ADMIN_DELETE.name())
                                .requestMatchers("/api/v1.1/users/customers/**").hasRole(ADMIN.name())
                                .requestMatchers(DELETE, "/api/v1.1/users/customers/**").hasAuthority(ADMIN_DELETE.name())
                                .requestMatchers("/api/v1/rooms/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/rooms/**").hasAnyAuthority(ADMIN_READ.name(), CUSTOMER_READ.name())
                                .requestMatchers(POST, "/api/v1/rooms/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/rooms/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/rooms/**").hasAuthority(ADMIN_DELETE.name())
                                .requestMatchers("/api/v1/users/**").hasRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/users/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/users/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/users/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/users/**").hasAuthority(ADMIN_DELETE.name())
                                .requestMatchers("/api/v1/reservations/**").hasAnyRole(ADMIN.name(),CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/reservations/**").hasAnyAuthority(ADMIN_READ.name(), CUSTOMER_READ.name())
                                .requestMatchers(POST, "/api/v1/reservations/**").hasAnyAuthority(ADMIN_CREATE.name(),CUSTOMER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/reservations/**").hasAnyAuthority(ADMIN_UPDATE.name(),CUSTOMER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/reservations/**").hasAuthority(ADMIN_DELETE.name())
                                .requestMatchers("/api/v1/billings/**").hasAnyRole(ADMIN.name(),CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/billings/**").hasAnyAuthority(ADMIN_READ.name(), CUSTOMER_READ.name())
                                .requestMatchers(POST, "/api/v1/billings/**").hasAnyAuthority(ADMIN_CREATE.name(),CUSTOMER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/billings/**").hasAnyAuthority(ADMIN_UPDATE.name(),CUSTOMER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/billings/**").hasAnyAuthority(ADMIN_DELETE.name(),CUSTOMER_DELETE.name())
                                .requestMatchers("/api/v1/facilities/**").hasAnyRole(ADMIN.name(),CUSTOMER.name())
                                .requestMatchers(GET, "/api/v1/facilities/**").hasAnyAuthority(ADMIN_READ.name(), CUSTOMER_READ.name())
                                .requestMatchers(POST, "/api/v1/facilities/**").hasAnyAuthority(ADMIN_CREATE.name(),CUSTOMER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/facilities/**").hasAnyAuthority(ADMIN_UPDATE.name(),CUSTOMER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/facilities/**").hasAnyAuthority(ADMIN_DELETE.name(),CUSTOMER_DELETE.name())
                                .requestMatchers("/api/v1/tasks").hasRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/tasks").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/tasks").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/tasks").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/tasks").hasAuthority(ADMIN_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
