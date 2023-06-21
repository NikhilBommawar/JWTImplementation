package com.unoveo.repository;

import com.unoveo.model.AppUser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Component
//@ComponentScan(basePackages = "com.unoveo")
//@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

  @Bean
  boolean existsByUsername(String username);

  @Bean
  AppUser findByUsername(String username);

  @Transactional
  @Bean
  void deleteByUsername(String username);

}
