package com.unoveo.jwt;


import com.unoveo.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.persistence.GeneratedValue;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@ComponentScan(basePackages = "{com.unoveo}")
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */
  @Autowired
  private Environment env;
  @Value("secret-key-ITSASECRETEKEY----------")
  private String secretKey;


  private long validityInMilliseconds = 3600000; // 1h

  @Autowired
  private MyUserDetails myUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  @Bean
  public String createToken(String username, List<Role> appUserRoles) {
    System.out.println("in JwtTokenProvider >>>>>>> createToken ");
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth", appUserRoles.stream().map(s -> new SimpleGrantedAuthority(s.getRolename())).filter(Objects::nonNull).collect(Collectors.toList()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setExpiration(validity)//
        .signWith(SignatureAlgorithm.HS256, secretKey)//
        .compact();
  }

  @Bean
  public Authentication getAuthentication(String token) {
    System.out.println("in JwtTokenProvider >>>>>>> getAuthentication ");
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  @Bean
  public String getUsername(String token) {
    System.out.println("JwtTokenProvider >>>>>>>>>> getUsername ");
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  @Bean
  public String resolveToken(HttpServletRequest req) throws IOException {
    System.out.println("JwtTokenProvider >>>>>>>>>> resolveToken ");
    String jwtToken = null;
   // String body = req.getReader().lines().collect(Collectors.joining()) ;
   // System.out.println("body ===>  "+body);


    String bearerToken = req.getHeader("Authorization");
    System.out.println("bearer token ==> "+ bearerToken);
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

  @Bean
  public boolean validateToken(String token) throws Exception {
    System.out.println("JwtTokenProvider >>>>>>>>>> validateToken ");
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new Exception();
    }
  }

}
