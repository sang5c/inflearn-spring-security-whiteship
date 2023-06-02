package com.example.inflearnspringsecuritywhiteship.account;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 여기서 반환하는 UserDetails가 곧 Authentication의 Principal이 된다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }

    @Transactional
    public Account createNew(Account account) {
        return createAccount(account.getUsername(), account.getRole(), account.getPassword());
    }

    @PostConstruct
    @Transactional
    public void init() {
        createAccount("pso", "USER", "123");
        createAccount("admin", "ADMIN", "123");
    }

    private Account createAccount(String username, String role, String password) {
        Account account = Account.create(username, password, role, passwordEncoder);
        return accountRepository.save(account);
    }
}
