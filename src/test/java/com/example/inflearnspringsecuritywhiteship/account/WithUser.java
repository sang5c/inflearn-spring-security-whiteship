package com.example.inflearnspringsecuritywhiteship.account;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "pso", roles = "USER")
public @interface WithUser {
}
