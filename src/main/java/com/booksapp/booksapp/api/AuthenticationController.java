package com.booksapp.booksapp.api;

import com.booksapp.booksapp.security.JWTModel;
import com.booksapp.booksapp.security.JWTProvider;
import com.booksapp.booksapp.security.JWTRedisService;
import com.booksapp.booksapp.security.LoginRequest;
import com.booksapp.booksapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JWTProvider jwtProvider;
    private UserServiceImpl userService;
    private JWTRedisService jwtRedisService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTProvider jwtProvider, UserServiceImpl userService,
                                    JWTRedisService jwtRedisService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.jwtRedisService = jwtRedisService;
    }

    @PostMapping("login")
    public ResponseEntity<JWTModel> login (@RequestBody LoginRequest loginRequest) {

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        System.out.println(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateJWT(authentication);
        return new ResponseEntity<>(new JWTModel(token), HttpStatus.OK);
    }
}
