package com.shop.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final ReactiveUserDetailsService userDetailsService;

    @Bean
    public SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchange ->
                        exchange
                                .pathMatchers("/admin/**").hasAuthority("ADMIN")
                                .pathMatchers("/categories/**").permitAll()
                                .pathMatchers("/cart/**").hasAuthority("USER")
                                .anyExchange().authenticated())
                .formLogin(formLoginSpec -> formLoginSpec.loginPage("/login").authenticationSuccessHandler(loginRedirectHandler()));
        return http.build();
    }

    @Bean
    public RedirectServerAuthenticationSuccessHandler loginRedirectHandler() {
        return new RedirectServerAuthenticationSuccessHandler("/all");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);

        authenticationManager.setPasswordEncoder(passwordEncoder());

        return authenticationManager;
    }
}
