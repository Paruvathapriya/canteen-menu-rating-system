package com.example.canteen.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

  private static final String SECRET = "super-secret-key-for-canteen-app-1234567890";
  private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

  public String generate(String username, String role) {
    return Jwts.builder()
      .setSubject(username)
      .claim("role", role)
      .setIssuedAt(new Date())
      .setExpiration(Date.from(Instant.now().plusSeconds(86400)))
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }

  public Claims parse(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}
