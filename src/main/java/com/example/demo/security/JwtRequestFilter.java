package com.example.demo.security;

import com.example.demo.models.UserSessionInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final long USER_SESSION_TIMEOUT_MINUTES = 2; // Thời gian tồn tại cho mỗi user session (2 phút)

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username1 = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username1 = jwtUtil.extractUsername(jwt);

            ////////////////////////////////////////////////////////////////////////////////////////////////
            String sessionId = null;
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("JSESSIONID".equals(cookie.getName())) {
                        sessionId = cookie.getValue();
                        break;
                    }
                }
            }

            // Nếu không có session ID, trả về lỗi UNAUTHORIZED
            if (sessionId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No session ID found");
                return;
            }

            // Lấy session từ session ID
            HttpSession session = request.getSession(false); // false để không tạo mới

            if (session != null && sessionId.equals(session.getId())) {
                Map<String, UserSessionInfo> userSessions = (Map<String, UserSessionInfo>) session.getAttribute("userSessions");

                if (userSessions != null && userSessions.containsKey(username1)) {
                    UserSessionInfo userSessionInfo = userSessions.get(username1);
                    LocalDateTime now = LocalDateTime.now();
//                    long minutesElapsed = ChronoUnit.MINUTES.between(userSessionInfo.getLoginTime(), now);

                    if (!userSessionInfo.getLoginTime().isAfter(now)) {
                        // Nếu session đã hết hạn, xóa user khỏi session
                        userSessions.remove(username1);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired for user: " + username1);
                        return; // Ngăn không cho tiếp tục xử lý request
                    } else {
                        // Reset thời gian đăng nhập
                        userSessionInfo.setLoginTime(LocalDateTime.now().plus(2, ChronoUnit.MINUTES));
                        userSessions.put(username1, userSessionInfo); // Cập nhật lại
                    }

                    // Cập nhật lại userSessions trong session
                    session.setAttribute("userSessions", userSessions);
                } else {
                    // Nếu username1 không tồn tại trong userSessions
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session for user: " + username1);
                    return; // Ngăn không cho tiếp tục xử lý request
                }
            } else {
                // Nếu session không tồn tại hoặc không trùng với session ID
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session ID");
                return; // Ngăn không cho tiếp tục xử lý request
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////
        }

        if (username1 != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username1);
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // Create authentication object here if needed
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); // Lưu đối tượng xác thực vào context
            }
        }
        chain.doFilter(request, response);
    }
}