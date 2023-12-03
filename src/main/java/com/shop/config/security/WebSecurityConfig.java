package com.shop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchange -> exchange.anyExchange().authenticated());
        return http.build();
    }
}
