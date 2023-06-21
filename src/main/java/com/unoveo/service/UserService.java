package com.unoveo.service;

import com.unoveo.jwt.JwtTokenProvider;
import com.unoveo.model.AppUser;
import com.unoveo.model.Role;
import com.unoveo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@ComponentScan
public class UserService {

//  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
//    this.userRepository = userRepository;
//    this.passwordEncoder = passwordEncoder;
//    this.jwtTokenProvider = jwtTokenProvider;
//    this.authenticationManager = authenticationManager;
//  }

  @Autowired
  private  UserRepository userRepository;
  @Autowired
  private  PasswordEncoder passwordEncoder;
  @Autowired
  private  JwtTokenProvider jwtTokenProvider;


  @Bean
  public String signin(String username, String password) throws Exception {
    try {
      AppUser appUser = userRepository.findByUsername(username);

      List<Role> authorities = appUser.getRoles()
              .stream()
              .collect(Collectors.toList());

      return jwtTokenProvider.createToken(username, authorities);

    } catch (AuthenticationException e) {
      throw new Exception();
    }
  }

  @Bean
  public String signup(AppUser appUser) throws Exception {
    if (!userRepository.existsByUsername(appUser.getUsername())) {
      appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
      userRepository.save(appUser);
      return jwtTokenProvider.createToken(appUser.getUsername(), (List<Role>) appUser.getRoles());
    } else {
      throw new Exception();
    }
  }

  @Bean
  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }

  @Bean
  public AppUser search(String username) throws Exception {
    AppUser appUser = userRepository.findByUsername(username);
    if (appUser == null) {
      throw new Exception();
    }
    return appUser;
  }

  @Bean
  public AppUser whoami(HttpServletRequest req) {
    System.out.println(">>>>>>>>>> find user");
    return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  @Bean
  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, (List<Role>) userRepository.findByUsername(username).getRoles());
  }

}
