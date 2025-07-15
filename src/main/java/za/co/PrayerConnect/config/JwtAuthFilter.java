package za.co.PrayerConnect.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import za.co.PrayerConnect.service.UserServ.UserService;

import za.co.PrayerConnect.util.JwtUtil;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userServices;

    @Autowired
    public JwtAuthFilter(JwtUtil jwtUtil, UserService userServices) {
        this.jwtUtil = jwtUtil;
        this.userServices = userServices;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userServices.findByEmail(email).ifPresent(user -> {
                    if (jwtUtil.isTokenValid(token, user.getEmail())) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(user, null, null);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                });
            }
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        System.out.println("JwtAuthFilter checking path: " + path);
        return path.equals("/api/admins/create") ||
                path.equals("/api/admins/authenticate") ||
                path.startsWith("/api/prayer-requests/") ||
                path.equals("/api/regular-users/create") ||
                path.startsWith("/api/admins/blockUser") ||
                path.startsWith("/api/admins/unblockUser") ||
                path.startsWith("/api/admins/deleteUser");
    }

}
