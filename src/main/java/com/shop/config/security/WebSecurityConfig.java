package com.shop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
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
}
