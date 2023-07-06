package com.ajcordenete.basekit.controller;

import com.ajcordenete.basekit.data.LoginRequest;
import com.ajcordenete.basekit.data.RegisterRequest;
import com.ajcordenete.basekit.entity.User;
import com.ajcordenete.basekit.service.AuthenticationService;
import com.ajcordenete.basekit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/helloWorld")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticate(loginRequest);
    }

}
