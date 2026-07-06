package com.example.canteen.config;

import com.example.canteen.repository.UserRepo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepo userRepo;

  public JwtFilter(JwtService jwtService, UserRepo userRepo) {
    this.jwtService = jwtService;
    this.userRepo = userRepo;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      try {
        Claims claims = jwtService.parse(token);
        String username = claims.getSubject();
        String role = (String) claims.get("role");

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          var user = userRepo.findByUsername(username).orElse(null);
          if (user != null) {
            var authToken = new UsernamePasswordAuthenticationToken(
              username, null, List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }
      } catch (Exception e) {
        // invalid token -> leave unauthenticated; downstream will reject if needed
      }
    }
    filterChain.doFilter(request, response);
  }
}
