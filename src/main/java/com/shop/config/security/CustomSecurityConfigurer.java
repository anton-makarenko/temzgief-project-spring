package com.shop.config.security;

import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class CustomSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomSecurityConfigurer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/categories/**").permitAll()
                .antMatchers("/cart/**").hasAnyAuthority("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/").usernameParameter("email").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").logoutSuccessHandler(userService).invalidateHttpSession(true).clearAuthentication(true).permitAll();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}
