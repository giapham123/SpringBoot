package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final Map<String, String> users = new HashMap<>();

    public CustomUserDetailsService() {
        // Dữ liệu người dùng tĩnh cho ví dụ
        users.put("user", "$2y$12$0.yS/K01LPQccJpjzoIrEOI3enYa8Jcj8QuWoUyEk7W62EUwp3BQm"); //password
        users.put("admin", "admin123");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + username);
        String password = users.get(username);
        if (password == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(password)
                .roles(username.equals("admin") ? "ADMIN" : "USER")
                .build();
    }
}
