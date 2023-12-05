package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.User;
import com.shop.enumeration.Role;
import com.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(Constants.WRONG_EMAIL_OR_PASSWORD));
    }

    public Page<User> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, Constants.USERS_PER_PAGE));
    }

    public Optional<User> getUserOptionalByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changeRole(long id, Role newRole) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such user"));
        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return null;
    }
}
