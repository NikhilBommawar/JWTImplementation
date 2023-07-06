package com.unoveo.jwt;


import com.unoveo.model.AppUser;
import com.unoveo.repository.UserRepository;

import com.unoveo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = "{com.unoveo}")
public class MyUserDetails implements UserDetailsService {
  @Autowired
  private  UserRepository userRepository;

//    private Collection<? extends GrantedAuthority> authorities;


    public MyUserDetails(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Bean
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    System.out.println(">>>>>>>> MyUserDetails <<<<<< loadUserByUsername ");
    System.out.println(userRepository.toString());
     AppUser appUser = userRepository.findByUsername(username);

      if (appUser == null) {
          throw new UsernameNotFoundException("User '" + username + "' not found");
      }


      GrantedAuthority authorities = appUser.getRoles()
              .stream().map(role -> new SimpleGrantedAuthority(role.getRolename()))
              .collect(Collectors.toList()).get(0);

      System.out.println(authorities.getAuthority().lines().collect(Collectors.toList()));

      System.out.println(" Granted Authorities of >>>>> " + username + " is " + authorities.toString() );



    return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(appUser.getPassword())//
        .authorities(authorities)//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
