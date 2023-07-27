package com.unoveo.security;


import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unoveo.jwt.JwtTokenProvider;
import com.unoveo.model.AppUser;
import com.unoveo.repository.UserRepository;
import com.unoveo.service.UserService;
import jakarta.servlet.ServletException;
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
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.util.ArrayList;
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
    private UserRepository userRepository;


    @GetMapping("/")
    public ResponseEntity<?> index() throws Exception {
        String jwt = null;
//        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            AppUser user = userRepository.findByUsername(currentUserName);
            String password = user.getPassword();

            System.out.println("in IndexController  username >>>>>>>>> " + currentUserName);
            System.out.println("in IndexController password >>>>>>>>> " + user.getPassword());
            jwt = userService.signin(currentUserName, password);
            System.out.println(userService.signin(currentUserName, password));

        }

        return ResponseEntity.ok(jwt);
    }

    @RequestMapping("/admin")
    public ResponseEntity<?> signup(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String inputUser = request.getReader().lines().collect(Collectors.joining());
        System.out.println(" >>>>>> input user >>>>>> " + inputUser);
        return ResponseEntity.ok("Entered Admin Zone successfully....");

//        return userService.signup(modelMapper.map(user, AppUser.class));
    }

    @RequestMapping("/guest")
    public ResponseEntity<?> user(HttpServletRequest request, HttpServletResponse response) throws IOException {


        return ResponseEntity.ok("Entered Guest Zone successfully....");

//        return userService.signup(modelMapper.map(user, AppUser.class));
    }

    @RequestMapping("/accessDenied")
    public ResponseEntity<?> noAccess() {
        return ResponseEntity.ok("Sorry Access Denied....");
    }


    @RequestMapping("/calc")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double result;


        String body = request.getReader().lines().collect(Collectors.joining());
        System.out.println("body ===> " + body);

        response.setContentType("application/json");

        // calling header methods for preflight
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Origin", "*");


        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);

        // (Method 1) ----> to read json from request and convert to String using Collectors
//            String exp2 = request.getReader().lines().collect(Collectors.joining());
        System.out.println("Got the expression from Frontend => " + body);

        Gson gson = new Gson();
//        String input = request.getParameter("inputModel"); // !!!!!!!!!!!!
        DoubleEvaluator eval = new DoubleEvaluator();

        // USING TYPETOKEN TO PARSE ARRAY OF INPUTMODEL
        ArrayList<InputModel> outputList = new Gson().fromJson(body, new TypeToken<ArrayList<InputModel>>() {
        }.getType());


        //  COUNTING THE LENGTH OF ARRAY
        int count = (int) outputList.stream().count();

        System.out.println("count = " + count);


        // ----------- SIR's LOGIC FOR MAIN CALCULATION  -------------------

        float firstOperand = 0;
        String operation = "";


        for (int index = 0; index < count; index++) {
            if (outputList.get(index).getType().equals("NUMBER")) {
                if (firstOperand == 0) {
                    firstOperand = Float.parseFloat(outputList.get(index).getValue());
                    if (index > 0 && outputList.get(index - 1).getValue().equals("subtract")) {
                        firstOperand = -firstOperand;

                    }
                } else {
                    firstOperand = evaluate(firstOperand, Float.parseFloat(outputList.get(index).getValue()), operation);
                }
            }
            if ((outputList.get(index).getType().equals("OPERATOR")) && (firstOperand != 0)) {
                operation = outputList.get(index).getValue();
            }

        }

        System.out.println("firstOperand " + firstOperand);
        response.getWriter().print(firstOperand);

    }


    // SWITCH CASE TO DECIDE THE OPERATION TO BE PERFORMED
    public float evaluate(float firstOperand, float secondOperand, String operation) {

        switch (operation) {

            case "add":
                firstOperand = doAddition(firstOperand, secondOperand);  // not used secondOperand variable
                break;

            case "subtract":
                firstOperand = doSubtraction(firstOperand, secondOperand);  // not used secondOperand variable
                break;

            case "multiply":
                firstOperand = doMultiplication(firstOperand, secondOperand);
                break;

            case "division":
                firstOperand = doDivision(firstOperand, secondOperand);
                break;

            default:
                break;

        }
        return firstOperand;
    }


    // METHODS TO PERFORM OPERATIONS
    private static float doAddition(float firstNo, float secondNo) {
        return firstNo + secondNo;
    }

    private static float doSubtraction(float firstNo, float secondNo) {
        return firstNo - secondNo;
    }

    private static float doMultiplication(float firstNo, float secondNo) {
        return firstNo * secondNo;
    }

    private static float doDivision(float firstNo, float secondNo) {
        return firstNo / secondNo;
    }


    //for Preflight Request
//    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);

    }

    //for Preflight Request
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
    }


    @RequestMapping("/getUser")
    public ResponseEntity<?> getUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("called getUser api");
        AppUser user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            user = userRepository.findByUsername(currentUserName);
         }
        return ResponseEntity.ok(user);
    }

}


