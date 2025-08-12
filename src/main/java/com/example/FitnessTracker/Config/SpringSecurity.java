package com.example.FitnessTracker.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity{
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/register/save", "/login", "/login/save", "/css/**","/img/**","/auth","/js/**","/welcome","/bmiNotlogin","/").permitAll()
                        .requestMatchers("/session","/api").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth")// usedto display the custom login page instead of the default Spring Security login page.
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login/save")//this should be same as in the form th:action field one
                        .defaultSuccessUrl("/home", true)//if success where user will redirect
                        .failureUrl("/login?error=true") //if fail where user will redirect
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/welcome?logout=true")//where to redirect when log out
                        .permitAll()
                );

        return http.build();
    }

    // Modern way to expose AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
