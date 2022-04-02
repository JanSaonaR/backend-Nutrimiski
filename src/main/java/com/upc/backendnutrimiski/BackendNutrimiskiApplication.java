package com.upc.backendnutrimiski;

import com.upc.backendnutrimiski.models.Nutritionist;
import com.upc.backendnutrimiski.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BackendNutrimiskiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendNutrimiskiApplication.class, args);
    }


    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/user/**", "/swagger-resources/**", "/preferences",
                            "/swagger-ui.html", "/v2/api-docs",
                            "/webjars/**", "/region/**").permitAll()
                    .anyRequest().authenticated();
        }
    }


}
