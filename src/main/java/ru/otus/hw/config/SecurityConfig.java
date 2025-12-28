package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(
                                "/auth/login",
                                "/error/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/pic/**",
                                "/webjars/**",
                                "/static/**"
                        ).permitAll()
                        .requestMatchers("/admin/**", "/user/create", "/user/edit", "/api/users/**", "/api/roles/**")
                        .hasRole("ADMIN")
                        .requestMatchers(
                                "/director/**",
                                "/user/add",
                                "/club/**",
                                "/logRecord/**",
                                "/pearls",
                                "/api/clubs/**",
                                "/api/logs/**",
                                "/api/log/**",
                                "/api/records/**",
                                "/api/scores/**",
                                "/api/pearls/**",
                                "/api/stat/**",
                                "/api/directors"
                        ).hasAnyRole("DIRECTOR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.sendError(HttpStatus.FORBIDDEN.value());
                            } else {
                                response.sendRedirect("/error/403");
                            }
                        })
                )
                .anonymous(Customizer.withDefaults())
                .rememberMe(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
