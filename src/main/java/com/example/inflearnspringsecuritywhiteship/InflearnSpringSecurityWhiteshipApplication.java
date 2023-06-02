package com.example.inflearnspringsecuritywhiteship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
@EnableAsync
public class InflearnSpringSecurityWhiteshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(InflearnSpringSecurityWhiteshipApplication.class, args);
    }

}

