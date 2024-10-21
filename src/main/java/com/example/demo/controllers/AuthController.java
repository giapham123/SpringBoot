package com.example.demo.controllers;

import com.example.demo.models.AuthRequest;
import com.example.demo.models.UserSessionInfo;
import com.example.demo.security.JwtUtil;
import com.example.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        HttpSession session = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lưu thông tin người dùng vào session
            // Tạo hoặc lấy session
            session = request.getSession(true); // true để tạo session nếu chưa có



            // Lấy Map userSessions từ session, nếu chưa có thì khởi tạo mới
            Map<String, UserSessionInfo> userSessions = (Map<String, UserSessionInfo>) session.getAttribute("userSessions");
            if (userSessions == null) {
                userSessions = new HashMap<>();
            }

            // Kiểm tra nếu user đã đăng nhập chưa
            String username = authRequest.getUsername();
            LocalDateTime now = LocalDateTime.now();

            // Kiểm tra nếu user đã đăng nhập và phiên chưa hết hạn
            if (userSessions.containsKey(username)) {
                UserSessionInfo userSessionInfo = userSessions.get(username);
                if (userSessionInfo.getLoginTime().isAfter(now)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User already logged in");
                } else {
                    // Nếu phiên đã hết hạn, cho phép đăng nhập lại
                    userSessions.remove(username);
                }
            }

            String ipAddress = request.getRemoteAddr();
            LocalDateTime tokenExpiry = LocalDateTime.now().plus(2, ChronoUnit.MINUTES);
            UserSessionInfo userSessionInfo = new UserSessionInfo(authRequest.getUsername(), ipAddress, tokenExpiry);
            userSessions.put(authRequest.getUsername(), userSessionInfo);
            session.setAttribute("userSessions", userSessions);
        } catch (Exception e) {
            System.out.println(e);
        }
        String jwt = jwtUtil.generateToken(authRequest.getUsername());
        return ResponseEntity.ok(jwt + "<br>" + session.getId());
    }

    @GetMapping("/secure-endpoint")
    public ResponseEntity<String> secureEndpoint() {
        return ResponseEntity.ok("This is a secure endpoint!");
    }
}