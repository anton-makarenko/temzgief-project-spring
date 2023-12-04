package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.User;
import com.shop.enumeration.Role;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class UserService implements ReactiveUserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    public Flux<User> getAllUsers(int page) {
        return userRepository.findAllByIdNotNull(PageRequest.of(page, Constants.USERS_PER_PAGE));
    }

    public Mono<User> getUserOptionalByEmail(String email) {
        return userRepository.findByEmail(email).mapNotNull(user -> user);
    }

    public Mono<User> changeRole(long id, Role newRole) {
        return userRepository.findById(id)
                .mapNotNull(u -> {
                    u.setRole(newRole);
                    return u;
                })
                .flatMap(userRepository::save);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username).mapNotNull(user ->
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .roles(user.getRole().toString()).build());
    }
}
