package com.tn.configSecurity;

import com.tn.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    private UserService userService;

    public ConfigSecurity(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // csrf: Cross-Site Request Forgery
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        // cần xác nhận các request, bất kỳ request nào cũng cần được xác nhận
        http.authorizeRequests().anyRequest().authenticated();

        // http basic dùng cho @RestController (gửi từ postman, ajax)
        // formLogin dùng cho @Controller việc gửi thẻ <form action method> từ trang html (Thymeleaf)
        http.httpBasic();
        http.csrf().disable();
    }
}
