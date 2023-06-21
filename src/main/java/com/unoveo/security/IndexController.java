package com.unoveo.security;


import com.unoveo.jwt.JwtTokenProvider;
import com.unoveo.model.AppUser;
import com.unoveo.repository.UserRepository;
import com.unoveo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;


/**
 * Controller for "/".
 *
 * @author Rob WInch
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private  UserRepository userRepository;


    @GetMapping("/")
    public ResponseEntity<?> index() throws Exception {
        String jwt = null;
//        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            AppUser user = userRepository.findByUsername(currentUserName);
            String password = user.getPassword();

            System.out.println("in IndexController  username >>>>>>>>> "+ currentUserName);
            System.out.println("in IndexController password >>>>>>>>> "+ user.getPassword());
           jwt = userService.signin(currentUserName,password);
            System.out.println(userService.signin(currentUserName,password));

        }

          return ResponseEntity.ok(jwt);
    }

    @RequestMapping("/signup")
   public void signup(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String inputUser = request.getReader().lines().collect(Collectors.joining());
        System.out.println(" >>>>>> input user >>>>>> "+ inputUser);

//        return userService.signup(modelMapper.map(user, AppUser.class));
   }

}