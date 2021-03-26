package com.booksapp.booksapp.api;

import com.booksapp.booksapp.model.dto.ForgotPassword;
import com.booksapp.booksapp.model.dto.ResetPasswordDTO;
import com.booksapp.booksapp.security.JWTModel;
import com.booksapp.booksapp.security.JWTProvider;
import com.booksapp.booksapp.security.JWTRedisService;
import com.booksapp.booksapp.security.LoginRequest;
import com.booksapp.booksapp.service.UserServiceImpl;
import com.booksapp.booksapp.service.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private EmailSender emailSender;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTProvider jwtProvider, UserServiceImpl userService,
                                    JWTRedisService jwtRedisService, EmailSender emailSender) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.jwtRedisService = jwtRedisService;
        this.emailSender = emailSender;
    }

    @PostMapping("login")
    public ResponseEntity<JWTModel> login (@RequestBody LoginRequest loginRequest) {

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateJWT(authentication);
        return new ResponseEntity<>(new JWTModel(token), HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout (@RequestHeader("Authorization") String jwt) {
        String userEmail = jwtProvider.getSubjectFromJWT(jwt);
        jwtRedisService.invalidateJWT(jwt, userEmail);
        System.out.println("You were logged out.");
        return ResponseEntity.ok().build();
    }

    @PostMapping("forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody ForgotPassword forgotPassword) {
        userService.getUserByEmail(forgotPassword.getEmail());
        String newPassword = userService.generateCommonLangPassword();
        userService.resetPassword(forgotPassword.getEmail(), newPassword, newPassword);
        emailSender.sendEmail(forgotPassword.getEmail(), "Please log in with this password, and then change id: " +
                newPassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("resetPassword")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO,
                                        @RequestHeader("Authorization") String jwt) {
        String userEmail = jwtProvider.getSubjectFromJWT(jwt);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEmail, resetPasswordDTO.getCurrentPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userService.resetPassword(userEmail, resetPasswordDTO.getNewPassword(), resetPasswordDTO.getConfirmedNewPassword());
        emailSender.sendEmail(userEmail, "Your password has been successfully changed.");
        return ResponseEntity.ok().build();
    }
}
