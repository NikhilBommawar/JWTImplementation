package com.unoveo.security;

import com.unoveo.jwt.JwtTokenFilter;
import com.unoveo.jwt.JwtTokenProvider;
import com.unoveo.jwt.MyUserDetails;
import com.unoveo.repository.UserRepository;
import com.unoveo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@SuppressWarnings("removal")
@ComponentScan(basePackages = "{com.unoveo}")
public class SecurityConfiguration {

    @Autowired
    private  JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;


     @Autowired
     private JwtTokenFilter customFilter;

    @Autowired
      private  MyUserDetails userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        System.out.println("SecurityFilterChain  >>>>>>>>>>>>>  filterChain ");

           http
                .cors(cors->cors.configurationSource(corsConfigurationSource())).csrf().disable()
                .authorizeHttpRequests(auth -> auth.requestMatchers("/admin").hasAuthority("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/guest").hasAuthority("GUEST"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/calc").hasAuthority("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").authenticated())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/getUser").hasAnyAuthority("ADMIN","USER"))
                .formLogin(withDefaults())
                .httpBasic();

        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    MyUserDetails customUserDetailsService() {
        return new MyUserDetails(userRepository);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


}