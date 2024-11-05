package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final CustomUserDetailsService userDetailsService;
//
//    public SecurityConfig(CustomUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/authenticate").permitAll() // Endpoint không cần xác thực
//                        .requestMatchers("/api/basic/**").authenticated() // Endpoint cần Basic Auth
//                        .anyRequest().authenticated() // Các yêu cầu khác cần xác thực
//                )
//                .httpBasic(Customizer.withDefaults()) // Sử dụng Basic Auth
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())) // Xử lý lỗi xác thực
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .anonymous(anonymous -> anonymous.disable());// Không sử dụng session
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//        return authenticationManagerBuilder.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12); // Sử dụng BCrypt để mã hóa mật khẩu
//    }

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/authenticate").permitAll()
                        .requestMatchers("/api/checkLogin").permitAll()
                        .requestMatchers("/send").permitAll()
                        .requestMatchers("/api/basic/**").authenticated() // Basic Auth cho các API bắt đầu bằng /api/basic/
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) //Actice Basic Auth
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())) // Xử lý xác thực Basic Auth
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // Không sử dụng session
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) // Thêm bộ lọc JWT
                .anonymous(anonymous -> anonymous.disable());
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) //Khởi tạo context 1 lần và k lặp lại khởi tạo context
////                .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class); Cách này sẽ khởi tạo lại context dấn đên việc null ở jwtUltil và mọi autowired trong JwtRequestFilter
        // Tắt anonymous authentication
        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // Sử dụng BCrypt để mã hóa mật khẩu
    }
}
