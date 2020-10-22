package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.User;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
