package com.example.inflearnspringsecuritywhiteship.form;

import com.example.inflearnspringsecuritywhiteship.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @Secured("ROLE_USER")
    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("==================");
        System.out.println(authentication);
        System.out.println(userDetails.getUsername());
    }

    /**
     * @Async를 사용하면 별도의 쓰레드에서 처리가 된다.
     * Security는 기본적으로 ThreadLocal을 사용하기 때문에 별도의 쓰레드에서는 SecurityContext를 공유하지 못한다.
     */
    @Async
    public void asyncService() {
        SecurityLogger.log("Async service");
        System.out.println("Async service is called");
    }
}
