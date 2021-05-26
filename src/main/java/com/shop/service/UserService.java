package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.User;
import com.shop.enumeration.Role;
import com.shop.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserService extends SimpleUrlLogoutSuccessHandler implements UserDetailsService, LogoutSuccessHandler {
    private static final Logger logger = LogManager.getLogger(UserService.class);

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
        logger.info("User {} has been registered", user.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser;
        if ((optionalUser = userRepository.findByEmail(s)).isPresent()) {
            User user = optionalUser.get();
            if (user.getAuthorities().contains(AuthorityUtils.createAuthorityList(Role.ADMIN.name()).get(0)))
                logger.info("Admin {} has logged in", user.getEmail());
            return user;
        }
        else
            throw new UsernameNotFoundException(Constants.WRONG_EMAIL_OR_PASSWORD);
    }

    public Page<User> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, Constants.USERS_PER_PAGE));
    }

    public Optional<User> getUserOptionalByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changeRole(long id, Role newRole) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            logger.error("Trying to set new role for user that does not exist");
            return new IllegalArgumentException("No such user");
        });
        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (authentication.getAuthorities().contains(AuthorityUtils.createAuthorityList(Role.ADMIN.name()).get(0)))
            logger.info("Admin {} has logged out", authentication.getName());
        super.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
