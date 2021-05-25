package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.User;
import com.shop.enumeration.Role;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        String unEncodedPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(unEncodedPassword);
        user.setPassword(encodedPassword);
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
}
