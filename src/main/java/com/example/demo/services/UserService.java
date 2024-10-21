package com.example.demo.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    public boolean isUserLoggedIn(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Kiểm tra xem thông tin người dùng có trong session không
            Object user = request.getSession().getAttribute("USER");
            return user != null;
        }
        return false;
    }
}
