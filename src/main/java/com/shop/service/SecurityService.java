package com.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final ReactiveAuthenticationManager authenticationManager;
    private final UserService userService;

    public Mono<Void> autoLogin(String email, String password) {
        return userService.findByUsername(email)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities()))
                .flatMap(authenticationManager::authenticate)
                .doOnNext(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication))
                .then();
    }
}
