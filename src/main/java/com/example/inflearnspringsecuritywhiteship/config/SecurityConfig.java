package com.example.inflearnspringsecuritywhiteship.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * favicon.ico 요청에 대한 응답을 보내지 않도록 설정
     * filterChain에 추가하는 방법도 있으나, 이 방법은 filterChain이 작동하지 않기에 소요시간이 더 짧다.
     * 참고: https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations(),
                        PathRequest.toH2Console()
                );
    }

    /**
     * 참고: https://stackoverflow.com/questions/74763256/accessdecisionvoter-deprecated-with-spring-security-6-x
     */
    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

    /**
     * 참고: https://docs.spring.io/spring-security/reference/servlet/configuration/java.html#jc-httpsecurity
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO: ContextHolder 전략 설정은 어디에 두면 좋을까
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/info", "/signup").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/signin")
                        .permitAll()
                )
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("cookieName")
                )
//                .sessionManagement(config -> config
//                        .sessionFixation()
//
//                )
                .build();
    }

    // 테스트용 계정 생성

    /**
     * 참고: https://www.baeldung.com/spring-security-basic-authentication
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("pso").password("{noop}123").roles("USER").and()
//                .withUser("admin").password("{noop}!@#").roles("ADMIN");
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // default: BCryptPasswordEncoder()
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
