package com.example.inflearnspringsecuritywhiteship.form;

import com.example.inflearnspringsecuritywhiteship.account.Account;
import com.example.inflearnspringsecuritywhiteship.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@SpringBootTest
class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }
    }

    @Test
    void dashboard() {
//        Account account = new Account("asdf", "123", "ADMIN");
//        accountService.createNew(account);

        UserDetails userDetails = accountService.loadUserByUsername("pso");
//        UserDetails userDetails = User.builder()
//                .username("tester")
//                .roles("TESTER")
//                .build();

//        User user = new User("tester", "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        sampleService.dashboard();
    }
    @Test
    void dashboard2() {
        Account account = new Account("user", "123", "USER");
        accountService.createNew(account);

//        UserDetails userDetails = accountService.loadUserByUsername("user");
//        UserDetails userDetails = User.builder()
//                .username("tester")
//                .roles("TESTER")
//                .build();

        User user = new User("tester", "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
//        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(token);

        sampleService.dashboard();
    }

}
